package com.example.taskmanagementsystem.domain.models.pk;

import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TaskTypePrimaryKey {
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_email", nullable = false)
    private UserEntity owner;

    @Column(name = "name", nullable = false)
    private String name;
}
