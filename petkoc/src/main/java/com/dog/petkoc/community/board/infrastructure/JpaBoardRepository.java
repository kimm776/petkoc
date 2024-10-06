package com.dog.petkoc.community.board.infrastructure;

import com.dog.petkoc.community.board.application.entity.Board;
import com.dog.petkoc.community.board.domain.repository.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBoardRepository extends JpaRepository<Board, Long>, BoardRepository {

    @Override
    default List<Board> getBoardList(){
        return findAll();
    }

    @Override
    default Board getBoardById(Long id){
        return findById(id).orElse(null);
    }

    @Override
    default Board modifyBoard(Board board) {
        return save(board);
    }

    @Override
    default void deleteBoard(Long id) {
        deleteById(id);
    }

}
