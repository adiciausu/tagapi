package com.cegeka.tag.tagapi.dto;

import com.cegeka.tag.tagapi.model.AnnotationShape;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDTO {
  private String id;
  private String name;
  private AnnotationShape shape;
  private String color;
  private String projectId;
}
