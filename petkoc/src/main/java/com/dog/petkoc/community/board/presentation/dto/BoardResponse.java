package com.dog.petkoc.community.board.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponse {

    private Long id;                  // 게시글 ID
    private String title;             // 게시글 제목
    private String content;           // 게시글 내용
    private String imageUrl;          // 이미지 URL
    private int likes;                // 좋아요 수
    private boolean isActive;         // 활성화 상태
    private LocalDateTime createdAt;  // 생성 시각
    private LocalDateTime updatedAt;  // 수정 시각

}
