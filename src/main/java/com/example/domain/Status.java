package com.example.domain;

import jakarta.persistence.EnumeratedValue;

public enum Status {
    PENDING(0),
    COMPLETED(1);

    @EnumeratedValue
    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
