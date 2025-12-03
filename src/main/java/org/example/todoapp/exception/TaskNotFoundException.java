package org.example.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super(String.format("%d non trouvé dans la base de données", id));
    }
}
