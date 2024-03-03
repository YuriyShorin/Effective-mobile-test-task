package ru.effectivemobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Wrong refresh token")
public class WrongRefreshTokenException extends RuntimeException {

    public WrongRefreshTokenException() {
        super();
    }
}
