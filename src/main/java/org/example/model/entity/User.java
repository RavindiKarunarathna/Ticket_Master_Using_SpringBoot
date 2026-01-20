package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    @Enumerated (EnumType.STRING) private Tier tier;
    public enum Tier{REGULAR, VIP, PLATINUM}
}
