package org.example.todoapp.controller;

import org.example.todoapp.exception.TaskNotFoundException;
import org.example.todoapp.model.Task;
import org.example.todoapp.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class TaskController {

    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Task> all() {
        return repository.findAll();
    }

    @PostMapping
    Task newTask(@RequestBody Task newTask) {
        return repository.save(newTask);
    }

    @GetMapping("/{id}")
    Task one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
