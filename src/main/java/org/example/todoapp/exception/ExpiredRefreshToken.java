package org.example.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ExpiredRefreshToken extends RuntimeException {
    public ExpiredRefreshToken() {
        super("Ce refresh token n'est plus valide");
    }
}
