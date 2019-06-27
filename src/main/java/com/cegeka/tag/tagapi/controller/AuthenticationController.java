package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.UserDTO;
import com.cegeka.tag.tagapi.model.User;
import com.cegeka.tag.tagapi.service.UserService;
import com.cegeka.tag.tagapi.service.jwt.JwtTokenProvider;
import java.net.URI;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;
  private JwtTokenProvider tokenProvider;
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  public AuthenticationController(AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO userDTO) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userDTO.getEmail(),
            userDTO.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok("\"" + jwt + "\"");
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO signUpRequest) {
    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Email is already taken!");
    }

    // Creating user's account
    User user = User.create(signUpRequest.getEmail(), signUpRequest.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User result = userService.save(user);

    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/user/{id}")
        .buildAndExpand(result.getId()).toUri();

    return ResponseEntity.created(location)
        .body(true);
  }
}
