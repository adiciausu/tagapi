package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.UserDTO;
import com.cegeka.tag.tagapi.dto.response.JwtAuthenticationResponseDTO;
import com.cegeka.tag.tagapi.model.User;
import com.cegeka.tag.tagapi.service.UserService;
import com.cegeka.tag.tagapi.service.jwt.JwtTokenProvider;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private UserService userService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthenticationController(AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JwtAuthenticationResponseDTO(jwt));
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
