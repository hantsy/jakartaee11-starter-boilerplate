package com.example.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "todos")
public class Todo extends AbstractEntity<Long> {
    private String title;

    private Status status = Status.PENDING;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Todo() {
    }

    public static Todo of(String title) {
        var todo = new Todo();
        todo.setTitle(title);
        return todo;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Todo todo)) return false;
        return Objects.equals(title, todo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id = " + this.getId() +
                ", title='" + title + "'" +
                ", status=" + status +
                ", createdAt=" + this.getCreatedAt() +
                ", lastModifiedAt=" + this.getLastModifiedAt() +
                '}';
    }
}
