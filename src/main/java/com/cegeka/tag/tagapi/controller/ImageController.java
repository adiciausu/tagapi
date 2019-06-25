package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ImageDTO;
import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.service.ImageService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @Autowired
  public ImageController(ImageService imageService, ModelMapper modelMapper) {
    this.imageService = imageService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/image")
  @ResponseBody
  public ImageDTO save(@RequestBody ImageDTO imageDTO) {
    Image image = this.modelMapper.map(imageDTO, Image.class);
    Image newImage = this.imageService.save(image);

    return modelMapper.map(newImage, ImageDTO.class);
  }

  @PostMapping("/image/upload")
  @ResponseBody
  public Boolean upload(@RequestParam("images[]") List<MultipartFile> files, @RequestParam("projectId") String projectId) {
    this.imageService.upload(files, projectId);

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
  public List<ImageDTO> findAll(@RequestParam("projectId") String projectId) {

    List<Image> imageList = this.imageService.findAll(projectId);
    List<ImageDTO> imageDTOList = new ArrayList<>();
    imageList.forEach((item) -> {
      ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
      imageDTOList.add(imageDTO);
    });

    return imageDTOList;
  }


  @RequestMapping(value = "/image/export")
  @ResponseBody
  public List<ImageDTO> txtResponse(@RequestParam("projectId") String projectId, HttpServletResponse response) {
    String fileName = "annotations.json";
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    List<Image> imageList = this.imageService.findAll(projectId);
    List<ImageDTO> imageDTOList = new ArrayList<>();
    imageList.forEach((item) -> {
      ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
      imageDTOList.add(imageDTO);
    });

    return imageDTOList;
  }

  @DeleteMapping("/image/{id}")
  @ResponseBody
  public String delete(@PathVariable String id) {
    this.imageService.delete(id);

    return "\"" + id + "\"";
  }
}
