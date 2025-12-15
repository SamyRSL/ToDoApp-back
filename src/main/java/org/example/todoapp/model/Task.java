package org.example.todoapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    private String description;
    private Instant creationDate;

    @Setter
    private boolean completed;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUserDetails user;

    protected Task() {
    }

    public Task(String description) {
        this.description = description;
        this.creationDate = Instant.now();
        this.completed = false;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description='" + description + '\'' + ", creationDate=" + creationDate + ", completed=" + completed + '}';
    }

    public TaskViewDTO toDTO() {
        return new TaskViewDTO(this.getId(), this.getDescription(), this.getCreationDate(), this.isCompleted());
    }

    public record TaskNewDTO(String description) {
    }

    public record TaskViewDTO(Long id, String description, Instant creationDate, boolean completed) {
    }
}
