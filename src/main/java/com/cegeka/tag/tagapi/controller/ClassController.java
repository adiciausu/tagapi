package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ClassDTO;
import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.service.ClassService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

  private ClassService classService;
  private ModelMapper modelMapper;

  @Autowired
  public ClassController(ClassService classService, ModelMapper modelMapper) {
    this.classService = classService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/class")
  @ResponseBody
  public Boolean save(@RequestBody ClassDTO classDTO) {
    Class clazz = this.modelMapper.map(classDTO, Class.class);
    this.classService.save(clazz);

    return true;
  }

  @DeleteMapping("/class/{id}")
  @ResponseBody
  public Boolean delete(@PathVariable String id) {
    this.classService.delete(id);

    return true;
  }

  @GetMapping("/class/list")
  @ResponseBody
  public List<ClassDTO> findAll() {
    List<Class> classList = this.classService.findAll();
    List<ClassDTO> classDTOList = new ArrayList<>();
    classList.forEach((item) -> {
      ClassDTO classDTO = modelMapper.map(item, ClassDTO.class);
      classDTOList.add(classDTO);
    });

    return classDTOList;
  }
}
