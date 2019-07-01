package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ImageDTO;
import com.cegeka.tag.tagapi.model.Annotation;
import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.model.UserPrincipal;
import com.cegeka.tag.tagapi.service.ClassService;
import com.cegeka.tag.tagapi.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ImageController {

    private ClassService classService;
    private ImageService imageService;
    private ModelMapper modelMapper;

    @Autowired
    public ImageController(ImageService imageService, ClassService classService, ModelMapper modelMapper) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
        this.classService = classService;
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
        List<Image> imageList = this.imageService.findAllByProjectId(projectId);
        List<ImageDTO> imageDTOList = new ArrayList<>();
        imageList.forEach((item) -> {
            ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
            imageDTOList.add(imageDTO);
        });

        return imageDTOList;
    }

    @GetMapping("/image/batch")
    @ResponseBody
    public List<ImageDTO> getAndLockBatch(@RequestParam("projectId") String projectId, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Image> imageList = this.imageService.getAndLockBatch(projectId, userPrincipal.getId());
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
        List<Image> imageList = this.imageService.findAllByProjectId(projectId);
        List<Class> classList = this.classService.findAll(projectId);
        List<ImageDTO> imageDTOList = new ArrayList<>();
        imageList.forEach((Image item) -> {
            ImageDTO imageDTO = modelMapper.map(item, ImageDTO.class);
            Map<String, Set<Annotation>> annotations = imageDTO.getAnnotations();
            for (String classId : item.getAnnotations().keySet()) {
                classList.forEach((Class clazz) -> {
                    if (clazz.getId().equals(classId)) {
                        annotations.put(clazz.getName(), annotations.get(classId));
                        annotations.remove(classId);
                    }
                });
            }
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
