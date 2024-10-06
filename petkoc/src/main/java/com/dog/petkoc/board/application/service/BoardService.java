package com.dog.petkoc.board.application.service;

import com.dog.petkoc.board.application.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> getBoardList();
    Board getBoardById(Long id);
    Board modifyBoard(Long id, Board board);
    void deleteBoard(Long id);

}
