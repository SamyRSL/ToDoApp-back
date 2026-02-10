package org.example.todoapp.controller;

import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class RegistrationController {


    private final CustomUserDetailsService customUserDetailsService;


    public RegistrationController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomUserDetails.RegisterRequestDTO req) {
        customUserDetailsService.register(req);

        return ResponseEntity.ok("User successfully registered");
    }
}
