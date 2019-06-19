package com.cegeka.tag.tagapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Annotation {
    AnnotationShape shape;
    List<Point> points;
}
