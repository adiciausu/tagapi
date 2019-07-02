package com.cegeka.tag.tagapi.dto;

import com.cegeka.tag.tagapi.model.Annotation;
import com.cegeka.tag.tagapi.model.ImageStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ImageDTO {

  private String id;
  private Map<String, Set<Annotation>> annotations = new HashMap<>();
  private Integer height;
  private Integer width;
  private String name;
  private String path;
  private String projectId;
  private ImageStatus status = ImageStatus.PENDING;
  private String processorUserId;
}
