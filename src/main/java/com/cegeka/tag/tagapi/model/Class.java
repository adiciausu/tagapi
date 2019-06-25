package com.cegeka.tag.tagapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Class {
  private String id;
  private String name;
  private AnnotationShape shape;
  private String color;
  private String projectId;
}
