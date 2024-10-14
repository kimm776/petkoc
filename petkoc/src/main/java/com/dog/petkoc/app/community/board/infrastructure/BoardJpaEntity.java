package com.dog.petkoc.app.community.board.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Builder
    public BoardJpaEntity(
            Long id,
            UUID memberId,
            Long boardId,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String imageUrl,
            Integer likes,
            Boolean isActive
    ) {
        this.id = id;
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
        this.likes = (likes != null) ? likes : 0;
        this.isActive = (isActive != null) ? isActive : true;
    }

    public static BoardJpaEntity toEntity(
            Long id,
            UUID memberId,
            Long boardId,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String imageUrl,
            Integer likes,
            Boolean isActive
    ) {
        return BoardJpaEntity.builder()
                .id(id)
                .memberId(memberId)
                .boardId(boardId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .imageUrl(imageUrl)
                .likes((likes != null) ? likes : 0)
                .isActive((isActive != null) ? isActive : true)
                .build();
    }
}
