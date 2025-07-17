package com.example.domain;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AuditListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable e) {
            e.setCreatedAt(Instant.now());
            e.setLastModifiedAt(Instant.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable e) {
            e.setLastModifiedAt(Instant.now());
        }
    }
}
