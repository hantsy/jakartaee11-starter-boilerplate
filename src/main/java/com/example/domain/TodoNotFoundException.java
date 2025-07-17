package com.example.domain;

import java.util.UUID;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Todo:" + id + " was not found.");
    }
}
