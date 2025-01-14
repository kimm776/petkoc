package com.dog.petkoc.app.community.infrastructure.board;

import com.dog.petkoc.app.community.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board,Long> {

}
