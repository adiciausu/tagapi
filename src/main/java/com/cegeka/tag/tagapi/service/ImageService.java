package com.cegeka.tag.tagapi.service;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.repo.ImageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageService {

  private ImageRepository imageRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public void save(Image image) {
    this.imageRepository.save(image);
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
}
