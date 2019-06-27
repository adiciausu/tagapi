package com.cegeka.tag.tagapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class User {

  @Id
  private String id;
  private String email;
  private String password;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static User create(String email, String password) {
    return new User(email, password);
  }
}
