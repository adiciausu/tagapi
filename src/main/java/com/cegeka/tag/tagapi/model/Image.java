package com.cegeka.tag.tagapi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Image {

  @Id
  private String id;
  private Map<String, Set<Annotation>> annotations = new HashMap<>();
  private Integer height;
  private Integer width;
  private String name;
  private String projectId;
}
