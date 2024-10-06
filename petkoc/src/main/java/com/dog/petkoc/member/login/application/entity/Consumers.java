package com.dog.petkoc.member.login.application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consumers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consumers {

//    @Id
//    @Column(name = "member_id", nullable = false, unique = true)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID memberId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
