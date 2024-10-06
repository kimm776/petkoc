package com.dog.petkoc.community.board.domain.repository;

import com.dog.petkoc.community.board.application.entity.Board;

import java.util.List;

public interface BoardRepository {

    List<Board> getBoardList();
    Board getBoardById(Long id);
    Board modifyBoard(Board board);
    void deleteBoard(Long id);

}
