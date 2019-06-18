package com.cegeka.tag.tagapi.model;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Image {

  @Id
  String id;
  Map<String, Set<Annotation>> annotations;
}
