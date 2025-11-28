package org.example.todoapp.model;

import java.util.Date;

public class Task {
    private static Integer counter = 0;
    private final Integer uid;
    private String description;
    private final Date creationDate;
    private boolean completed;

    public Task(String description) {
        this.uid = Task.counter;
        Task.counter++;
        this.description = description;
        this.creationDate = new Date();
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getUid() {
        return uid;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
