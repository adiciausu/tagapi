package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ClassDTO;
import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.service.ClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
