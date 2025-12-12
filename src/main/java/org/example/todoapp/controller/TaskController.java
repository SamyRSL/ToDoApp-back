package org.example.todoapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.todoapp.exception.TaskNotFoundException;
import org.example.todoapp.model.CustomUserDetails;
import org.example.todoapp.model.Task;
import org.example.todoapp.repository.TaskRepository;
import org.example.todoapp.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class TaskController {

    private final TaskRepository repository;
    private final TaskService service;

    TaskController(TaskRepository repository, TaskService taskService) {
        this.repository = repository;
        this.service = taskService;
    }

    @GetMapping
    List<Task> all() {
        return repository.findAll();
    }

    @PostMapping
    Task newTask(@RequestBody TaskDTO dto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("authenticated user is :{}", customUserDetails.getUsername());
        return service.createTask(dto, customUserDetails);
    }

    @GetMapping("/{id}")
    Task one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }

    public record TaskDTO(String description) {
    }
}
