package com.cegeka.tag.tagapi.service;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.repo.ImageRepository;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageService {

  private ImageRepository imageRepository;
  private Path uploadPath;

  @Autowired
  public ImageService(ImageRepository imageRepository,
      @Value("${app.uploadPath}") String uploadURI) {
    this.imageRepository = imageRepository;
    this.uploadPath = Paths.get(uploadURI);

    // @todo move this to @PostConstruct???
    try {
      Files.createDirectories(this.uploadPath);
      Files.createDirectories(Paths.get(this.uploadPath.toString(), "images"));
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }

  public Image save(Image image) {
    return this.imageRepository.save(image);
  }

  public void upload(List<MultipartFile> imageList) {
    for (MultipartFile file : imageList) {
      try (InputStream inputStream = file.getInputStream()) {
        Path newFilePath = getImagePath(file.getOriginalFilename());
        Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);

        BufferedImage bufferedImage = ImageIO.read(new File(newFilePath.toString()));
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());
        this.save(image);

      } catch (IOException e) {
        throw new RuntimeException("Failed to store file ", e);
      }
    }
  }

  public Image findById(String imageId) {
    Optional<Image> imageOptional = this.imageRepository.findById(imageId);

    return imageOptional.orElse(null);
  }

  public List<Image> findAll() {
    List<Image> imageList = new ArrayList<>();
    this.imageRepository.findAll().forEach(imageList::add);

    return imageList;
  }

  public void delete(String imageId) {
    Image img = this.imageRepository.findById(imageId).orElse(null);
    if (img != null) {
      try {
        Files.delete(getImagePath(img.getName()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.imageRepository.deleteById(imageId);
  }

  private Path getImagePath(String fileName) {
    return Paths.get(this.uploadPath.toString(), "images", fileName);
  }
}
