package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Email not found")
public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super();
    }
}
