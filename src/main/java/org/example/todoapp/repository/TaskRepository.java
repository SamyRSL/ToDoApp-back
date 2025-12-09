package org.example.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.example.todoapp.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDescription(String description);
    Task findById(long id);
}
