package com.dog.petkoc.app.community.board.infrastructure;

import com.dog.petkoc.app.community.board.domain.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    /**
     * JPA 엔티티를 도메인 엔티티로 변환하는 메서드.
     *
     * @param entity JPA에서 사용되는 BoardJpaEntity 객체
     * @return 도메인에서 사용하는 Board 객체
     */
    public Board mapToDomainEntity(BoardJpaEntity entity) {
        return Board.generateBoard(
                entity.getId(),
                entity.getMemberId(),
                entity.getBoardId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getImageUrl(),
                entity.getLikes(),
                entity.getIsActive()
        );
    }

}
