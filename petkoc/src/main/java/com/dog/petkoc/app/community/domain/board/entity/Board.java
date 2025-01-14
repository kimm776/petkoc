package com.dog.petkoc.app.community.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private int likes;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    private String title;

    public Board(Long boardId, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
                 String imageUrl, boolean isActive, int likes, UUID memberId, String title) {
        this.boardId = boardId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.likes = likes;
        this.memberId = memberId;
        this.title = title;
    }
}
