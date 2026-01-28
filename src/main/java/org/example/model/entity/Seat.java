package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Seat {
    @Id @GeneratedValue private Long id;
    @ManyToOne private Event event;
    private String seatNumber;
    @Enumerated (EnumType.STRING) private Status status=Status.AVAILABLE;
    private Long heldByUserId;
    private LocalDateTime holdExpiry;

    public enum Status{AVAILABLE, HELD, SOLD}
}
