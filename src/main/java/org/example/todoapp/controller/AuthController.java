package org.example.todoapp.controller;

import jakarta.validation.Valid;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.RefreshToken;
import org.example.todoapp.service.CustomUserDetailsService;
import org.example.todoapp.service.JwtService;
import org.example.todoapp.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    AuthenticationManager authManager;
    CustomUserDetailsService customUserDetailsService;
    JwtService jwtService;
    RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authManager, CustomUserDetailsService customUserDetailsService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public CustomUserDetails.LoginResponse login(@RequestBody @Valid final CustomUserDetails.LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password()));
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.generate(user);
        return new CustomUserDetails.LoginResponse(jwtService.generateToken(loginRequestDTO.username()), refreshToken.getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String header) {
        String refreshToken = header.substring(7);
        refreshTokenService.revokeByToken(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public CustomUserDetails.TokenDTO refresh(@RequestBody String refreshToken) {

        RefreshToken token = refreshTokenService.verify(refreshToken);
        String newAccessToken = jwtService.generateToken(token.getUser().getUsername());
        return new CustomUserDetails.TokenDTO(newAccessToken);
    }

}
