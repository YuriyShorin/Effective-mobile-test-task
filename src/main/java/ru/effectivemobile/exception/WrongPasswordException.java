package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Wrong password")
public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super();
    }
}
