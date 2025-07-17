package com.example.domain;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Todo:" + id + " was not found.");
    }
}
