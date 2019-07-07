package com.emre.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFountException extends RuntimeException {
    public PostNotFountException(String message) {
        super(message);
    }
}
