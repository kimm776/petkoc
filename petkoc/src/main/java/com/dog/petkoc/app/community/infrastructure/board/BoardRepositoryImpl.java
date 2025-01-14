package com.dog.petkoc.app.community.infrastructure.board;

import com.dog.petkoc.app.community.domain.board.entity.Board;
import com.dog.petkoc.app.community.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public List<Board> findAll() {
        return boardJpaRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findById(id);
    }
}
