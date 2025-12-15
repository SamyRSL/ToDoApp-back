package org.example.todoapp.repository;

import org.example.todoapp.model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import org.example.todoapp.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDescription(String description);

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userID")
    List<Task> findByUserID(@Param("userID") Long userID);
}
