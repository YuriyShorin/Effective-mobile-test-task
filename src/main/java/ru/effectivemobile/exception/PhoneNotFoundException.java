package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Phone not found")
public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException() {
        super();
    }
}