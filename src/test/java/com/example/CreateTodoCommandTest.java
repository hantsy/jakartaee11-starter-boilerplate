package com.example;

import com.example.rest.CreateTodoCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTodoCommandTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void title() {
        var todo =  CreateTodoCommand.of("test");
        assertEquals("test", todo.title());
    }
}