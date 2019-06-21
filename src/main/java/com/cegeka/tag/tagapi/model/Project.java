package com.cegeka.tag.tagapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Project {
  @Id
  private String id;
  private String name;
}
