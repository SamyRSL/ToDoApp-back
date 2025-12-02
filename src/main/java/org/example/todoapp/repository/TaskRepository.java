package org.example.todoapp.repository;

import org.springframework.data.repository.CrudRepository;

import org.example.todoapp.model.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByDescription(String description);
    Task findById(long id);
}
