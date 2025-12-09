package org.example.todoapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }
}
