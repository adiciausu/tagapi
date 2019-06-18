package com.cegeka.tag.tagapi.model;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Image {
  Long id;
  Map<String, Set<Annotation>> annotations;
}
