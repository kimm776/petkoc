package com.dog.petkoc.app.community.presentation.board;

import com.dog.petkoc.app.community.application.board.dto.BoardDto;
import com.dog.petkoc.app.community.application.board.service.BoardService;
import com.dog.petkoc.app.community.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "게시판 API", description = "게시판 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판 목록 조회 API", description = "게시판 목록을 조회합니다.")
    @GetMapping("/")
    public List<BoardDto> getBoardList() {
        return boardService.getBoardList();
    }

    @Operation(summary = "게시판 상세 조회 API", description = "게시판을 상세 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long id) {
        BoardDto boardDto = boardService.getBoardById(id);
        if (boardDto == null) {
            return ResponseEntity.notFound().build();  // 게시글이 없으면 404 응답
        }
        return ResponseEntity.ok(boardDto);  // 게시글을 찾으면 200 응답
    }
}


















