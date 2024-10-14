package com.dog.petkoc.app.community.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, Long> {

    default List<BoardJpaEntity> getBoardList() {
        return findAll();
    }
//
//    default Board getBoardById(Long id){
//        return findById(id).orElse(null);
//    }
//
//    default Board modifyBoard(Board board) {
//        return save(board);
//    }
//
//    default void deleteBoard(Long id) {
//        deleteById(id);
//    }
}
