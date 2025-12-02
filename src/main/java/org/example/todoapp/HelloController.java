package org.example.todoapp;

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

    @GetMapping("/v2")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello WORLD!");
    }
}
