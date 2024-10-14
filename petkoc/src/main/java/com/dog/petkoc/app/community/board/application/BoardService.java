package com.dog.petkoc.app.community.board.application;

import com.dog.petkoc.app.community.board.domain.Board;
import com.dog.petkoc.app.community.board.domain.BoardRepository;
import com.dog.petkoc.app.community.board.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 전체 조회 API
     *
     * @return 전체 게시글 목록.
     */
    public List<Board> getBoardList() {
        // 강의 조회
        List<Board> boardList = boardRepository.getBoardList();

        if (boardList.isEmpty()) {
            throw new NotFoundException("게시글 목록이 없습니다.");
        }

        return boardList;
    }


//    public Board getBoardById(Long id) {
//        return boardRepository.getBoardById(id);
//    }
//
//    public Board modifyBoard(Long id, Board board) {
//        if (id == null) { // 등록
//            // todo - 멤버 아이디 처리
//            return boardRepository.modifyBoard(board);
//        } else { // 수정
//            Board updateBoard = boardRepository.getBoardById(id);
//
//            // 업데이트
//            updateBoard.setTitle(board.getTitle());
//            updateBoard.setContent(board.getContent());
//
//            return boardRepository.modifyBoard(updateBoard);
//        }
//    }
//
//    public void deleteBoard(Long id) {
//        boardRepository.deleteBoard(id);
//    }
}
