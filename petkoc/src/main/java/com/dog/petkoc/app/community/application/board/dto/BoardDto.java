package com.dog.petkoc.app.community.application.board.dto;

import com.dog.petkoc.app.community.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BoardDto {
    private final Long id;
    private final Long boardId;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final boolean isActive;
    private final int likes;
    private final UUID memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Board 엔티티 -> DTO로 변환
    public static BoardDto fromEntity(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .imageUrl(board.getImageUrl())
                .isActive(board.isActive())
                .likes(board.getLikes())
                .memberId(board.getMemberId())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
