package com.example.taskmanagementsystem.domain.models.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comment_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "author_email", nullable = false)
    private UserEntity author;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @Builder.Default
    @Column(name = "writted_at", nullable = false)
    private LocalDateTime writtedAt = LocalDateTime.now();

}