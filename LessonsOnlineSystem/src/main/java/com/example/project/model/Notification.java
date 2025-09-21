package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private Role targetRole;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public enum Role {
        TEACHER,
        STUDENT,
        ADMIN
    }
}
