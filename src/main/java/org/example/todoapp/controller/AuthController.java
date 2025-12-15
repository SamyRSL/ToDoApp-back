package org.example.todoapp.controller;

import jakarta.validation.Valid;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.service.CustomUserDetailsService;
import org.example.todoapp.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    AuthenticationManager authManager;
    CustomUserDetailsService customUserDetailsService;
    JwtService jwtService;

    public AuthController(AuthenticationManager authManager, CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.authManager = authManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public CustomUserDetails.LoginResponseDTO login(@RequestBody @Valid final CustomUserDetails.LoginRequestDTO loginRequestDTO) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password()));
        } catch (final BadCredentialsException _) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new CustomUserDetails.LoginResponseDTO(jwtService.generateToken(loginRequestDTO.username()));
    }

}
