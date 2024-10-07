package com.dog.petkoc.community.board.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

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
    @Builder.Default
    private Integer likes = 0;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

//    @ManyToOne
//    @JoinColumn(name = "member_id", referencedColumnName = "member_id", foreignKey = @ForeignKey(name = "community_posts_member_id_fkey"))
//    private Consumers consumers;  // 외래 키 관계
//
//    @ManyToOne
//    @JoinColumn(name = "board_id", referencedColumnName = "board_id", foreignKey = @ForeignKey(name = "community_posts_board_id_fkey"))
//    private BoardCode boardCode;  // 외래 키 관계

}