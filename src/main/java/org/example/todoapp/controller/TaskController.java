package org.example.todoapp.controller;

import lombok.extern.slf4j.Slf4j;
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
    public List<Task> all() {
        return repository.findAll();
    }

    @GetMapping("/my")
    public List<Task.TaskViewDTO> userTasks(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("Getting tasks from user : {}", user.getUsername());
        return service.getTasks(user);
    }

    @PostMapping
    public Task newTask(@RequestBody Task.TaskNewDTO dto, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("authenticated user is : {}", user.getUsername());
        return service.createTask(dto, user);
    }

    @GetMapping("/{id}")
    public Task one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
