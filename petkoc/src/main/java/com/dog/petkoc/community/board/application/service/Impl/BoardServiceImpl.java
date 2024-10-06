package com.dog.petkoc.community.board.application.service.Impl;

import com.dog.petkoc.community.board.application.entity.Board;
import com.dog.petkoc.community.board.application.exception.NotFoundException;
import com.dog.petkoc.community.board.application.service.BoardService;
import com.dog.petkoc.community.board.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> getBoardList(){
        List<Board> boardList = boardRepository.getBoardList();

        if (boardList.isEmpty()) {
            throw new NotFoundException("게시글 목록이 없습니다.");
        }
        return boardList;
    }

    @Override
    public Board getBoardById(Long id) {
        Board board = boardRepository.getBoardById(id);
        return board;
    }

    @Override
    public Board modifyBoard(Long id, Board board) {
        if (id == null) { //등록
            //todo-멤버아이디
            return boardRepository.modifyBoard(board);
        } else { //수정
            Board updateBoard = boardRepository.getBoardById(id);

            // 업데이트
            updateBoard.setTitle(board.getTitle());
            updateBoard.setContent(board.getContent());

            return boardRepository.modifyBoard(updateBoard);
        }
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteBoard(id);
    }

}
