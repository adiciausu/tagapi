package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ImageAnnotationsDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnotationController {

  @PostMapping("/annotation")
  @ResponseBody
  public Map save(@RequestBody ImageAnnotationsDTO imageAnnotationsDTO) {
    System.out.println("X");

    return new HashMap();
  }
}
