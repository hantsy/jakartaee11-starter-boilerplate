package com.example.web;

import jakarta.validation.constraints.NotBlank;


public class TodoForm {
    Long id;

    @NotBlank
    String title;

    public TodoForm(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public TodoForm() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TodoForm{" + "id=" + id + ", title=" + title + '}';
    }


}

//public record TodoForm(
//        UUID id,
//        @NotBlank
//        String title
//) {
//}

