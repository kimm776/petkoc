package com.dog.petkoc.app.community.application.board.service;

import com.dog.petkoc.app.community.application.board.dto.BoardDto;
import com.dog.petkoc.app.community.domain.board.entity.Board;
import com.dog.petkoc.app.community.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시판 목록 조회
    public List<BoardDto> getBoardList() {
        return boardRepository.findAll().stream()
                .map(BoardDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 게시판 상세 조회
    public BoardDto getBoardById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        return boardOptional.map(BoardDto::fromEntity).orElse(null);
    }
}
