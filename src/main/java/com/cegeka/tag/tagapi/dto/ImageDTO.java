package com.cegeka.tag.tagapi.dto;

import com.cegeka.tag.tagapi.model.Annotation;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ImageDTO {
  private String id;
  private Map<String, Set<Annotation>> annotations;
}
