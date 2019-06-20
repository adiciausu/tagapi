package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ImageDTO;
import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.service.ImageService;
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
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

  private ImageService imageService;
  private ModelMapper modelMapper;
  private Path uploadPath;

  @Autowired
  public ImageController(ImageService imageService, ModelMapper modelMapper,
      @Value("${app.uploadPath}") String uploadURI) {
    this.imageService = imageService;
    this.modelMapper = modelMapper;
    this.uploadPath = Paths.get(uploadURI);

    // @todo move this to startup script
    try {
      Files.createDirectories(this.uploadPath);
      Files.createDirectories(Paths.get(this.uploadPath.toString(), "images"));
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }

  @PostMapping("/image")
  @ResponseBody
  public Boolean save(@RequestBody ImageDTO imageDTO) {
    Image image = this.modelMapper.map(imageDTO, Image.class);
    this.imageService.save(image);

    return true;
  }

  @PostMapping("/image/upload")
  @ResponseBody
  public Boolean upload(@RequestParam("images[]") List<MultipartFile> files) {
    for (MultipartFile file : files) {
      try (InputStream inputStream = file.getInputStream()) {
        Path newFilePath = Paths
            .get(this.uploadPath.toString(), "images", file.getOriginalFilename());
        Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);

        BufferedImage bufferedImage = ImageIO.read(new File(newFilePath.toString()));
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());
        this.imageService.save(image);

      } catch (IOException e) {
        throw new RuntimeException("Failed to store file ", e);
      }
    }

    return true;
  }

  @GetMapping("/image")
  @ResponseBody
  public ImageDTO findByID(@RequestParam String imageId) {
    Image image = this.imageService.findById(imageId);

    return modelMapper.map(image, ImageDTO.class);
  }

  @GetMapping("/image/list")
  @ResponseBody
  public List<ImageDTO> findAll() {
    List<Image> imageList = this.imageService.findAll();
    List<ImageDTO> imageDTOList = new ArrayList<>();
    imageList.forEach((item) -> {
      ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
      imageDTOList.add(imageDTO);
    });

    return imageDTOList;
  }


  @RequestMapping(value ="/image/export" )
  @ResponseBody
  public List<ImageDTO> txtResponse(HttpServletResponse response){
    String fileName = "annotations.json";
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    List<Image> imageList = this.imageService.findAll();
    List<ImageDTO> imageDTOList = new ArrayList<>();
    imageList.forEach((item) -> {
      ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
      imageDTOList.add(imageDTO);
    });

    return imageDTOList;
  }
}
