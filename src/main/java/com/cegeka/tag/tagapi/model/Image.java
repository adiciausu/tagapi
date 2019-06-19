package com.cegeka.tag.tagapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class Image {
    @Id
    private String id;
    private Map<String, Set<Annotation>> annotations;
    private Integer height;
    private Integer width;
    private String name;
    private String path;
}
