package com.dog.petkoc.board.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

    private String title;     // 게시글 제목
    private String content;   // 게시글 내용
    private String imageUrl;  // 이미지 URL

}
