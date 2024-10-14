package com.dog.petkoc.app.community.board.infrastructure;

import com.dog.petkoc.app.community.board.domain.Board;
import com.dog.petkoc.app.community.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;
    private final BoardMapper boardMapper;

    /**
     * 게시글 전체 조회 API
     *
     * @return 전체 게시글 목록.
     */
    @Override
    public List<Board> getBoardList() {
        // 강의 ID 목록과 시작 시간을 기준으로 강의 엔티티를 조회한 후 도메인 객체로 변환
        return boardJpaRepository.getBoardList().stream()
                .map(boardMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

}
