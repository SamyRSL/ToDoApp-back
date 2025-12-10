package org.example.todoapp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public AuthResponse login(@RequestBody @Valid final AuthRequest authRequest) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new AuthResponse(jwtService.generateToken(authRequest.username));
    }

    public record AuthRequest(@NotBlank String username, @NotBlank String password) {
    }

    public record AuthResponse(String token) {
    }
}
