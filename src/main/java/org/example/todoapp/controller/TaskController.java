package org.example.todoapp.controller;

import org.example.todoapp.exception.TaskNotFoundException;
import org.example.todoapp.model.Task;
import org.example.todoapp.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/tasks")
    List<Task> all() {
        return repository.findAll();
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody Task newTask) {
        return repository.save(newTask);
    }

    @GetMapping("/tasks/{id}")
    Task one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
