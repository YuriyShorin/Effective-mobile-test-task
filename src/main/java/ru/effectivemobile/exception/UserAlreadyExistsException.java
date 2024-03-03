package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with such email already exists")
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }
}
