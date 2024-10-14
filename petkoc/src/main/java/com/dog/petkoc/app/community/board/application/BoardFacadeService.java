package com.dog.petkoc.app.community.board.application;

import com.dog.petkoc.app.community.board.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardFacadeService {

    private final BoardService boardService;

    /**
     * 게시글 전체 조회 API
     *
     * @return 전체 게시글 목록.
     */
    public List<Board> getBoardList() {

        List<Board> boardList = boardService.getBoardList();

        return boardList;
    }

}
