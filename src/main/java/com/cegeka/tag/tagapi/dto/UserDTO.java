package com.cegeka.tag.tagapi.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
