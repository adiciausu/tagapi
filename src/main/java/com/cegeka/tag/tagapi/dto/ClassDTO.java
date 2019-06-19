package com.cegeka.tag.tagapi.dto;

import com.cegeka.tag.tagapi.model.AnnotationShape;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDTO {
    String id;
    String name;
    AnnotationShape shape;
    String color;
}
