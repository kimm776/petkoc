package com.dog.petkoc.app.community.board.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Board {
    private Long id;
    private UUID memberId;
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;
    private Integer likes;
    private Boolean isActive;

    public static Board generateBoard(
            Long id,
            UUID memberId,
            Long boardId,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String imageUrl,
            Integer likes,
            Boolean isActive) {
        return new Board(
                id,
                memberId,
                boardId,
                title,
                content,
                createdAt,
                updatedAt,
                imageUrl,
                (likes != null) ? likes : 0,
                (isActive != null) ? isActive : true
        );
    }
}
