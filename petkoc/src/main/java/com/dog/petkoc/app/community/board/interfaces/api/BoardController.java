package com.dog.petkoc.app.community.board.interfaces.api;

import com.dog.petkoc.app.community.board.application.BoardFacadeService;
import com.dog.petkoc.app.community.board.domain.Board;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "커뮤니티 API", description = "커뮤니티 API입니다.")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardFacadeService boardFacadeService;

    /**
     * 게시글 전체 조회 API
     * @return - 전체 게시글 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Board>> getBoardList() {
        return ResponseEntity.ok(boardFacadeService.getBoardList());
    }


//    // 게시글 전체 조회
//    @GetMapping
//    public ResponseEntity<List<BoardResponse>> getBoardList() {
//        List<BoardResponse> boardList = boardService.getBoardList();
//        return ResponseEntity.ok(boardList);
//    }
//
//    // 게시글 상세 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
//        Board board = boardService.getBoardById(id);
//        return ResponseEntity.ok(board);
//    }
//
//    // 게시글 등록
//    @PostMapping
//    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
//        //todo-멤버아이디 가져오기
//        Board createdBoard = boardService.modifyBoard(null, board);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
//    }
//
//    // 게시글 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody Board board) {
//        Board updatedBoard = boardService.modifyBoard(id, board);
//        return ResponseEntity.ok(updatedBoard);
//    }
//
//    // 게시글 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
//        boardService.deleteBoard(id);
//        return ResponseEntity.noContent().build();
//    }

}
