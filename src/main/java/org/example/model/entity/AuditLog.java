package org.example.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class AuditLog {
    @Id @GeneratedValue private Long id;
    private Long userID;
    private String action;
    private String details;
    private LocalDateTime timestamp = LocalDateTime.now();
}
