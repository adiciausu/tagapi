package com.cegeka.tag.tagapi.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Annotation {
  AnnotationShape shape;
  Set<Point> points;
}
