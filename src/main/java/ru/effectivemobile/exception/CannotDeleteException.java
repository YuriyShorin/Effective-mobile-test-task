package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cannot delete")
public class CannotDeleteException extends RuntimeException {

    public CannotDeleteException(String message) {
        super(message);
    }
}
