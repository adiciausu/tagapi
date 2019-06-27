package com.cegeka.tag.tagapi.service;

import com.cegeka.tag.tagapi.model.User;
import com.cegeka.tag.tagapi.model.UserPrincipal;
import com.cegeka.tag.tagapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email).orElse(null);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() ->
            new UsernameNotFoundException(
                "User not found with username or email : " + email)
        );

    return UserPrincipal.create(user);
  }

  public UserDetails loadUserById(String id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> new UsernameNotFoundException("User not found with id : " + id)
    );

    return UserPrincipal.create(user);
  }

  public User save(User user) {
    return this.userRepository.save(user);
  }
}