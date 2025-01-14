package com.dog.petkoc.app.community.domain.board.repository;

import com.dog.petkoc.app.community.domain.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<Board> findAll();

    Optional<Board> findById(Long id);
}
