package com.cegeka.tag.tagapi.model;

import java.util.Collection;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
public class UserPrincipal implements org.springframework.security.core.userdetails.UserDetails {

  @Id
  private String id;
  private String email;
  private String password;

  public static UserPrincipal create(User user) {
    return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword());
  }

  public UserPrincipal(String id, String email, String password) {
    this.email = email;
    this.id = id;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
