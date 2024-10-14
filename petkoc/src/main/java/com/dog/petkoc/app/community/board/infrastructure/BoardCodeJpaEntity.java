package com.dog.petkoc.app.community.board.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boardCode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardCodeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Builder
    public BoardCodeJpaEntity(
            Long id,
            String name,
            String description
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static BoardCodeJpaEntity toEntity(
            Long id,
            String name,
            String description
    ) {
        return BoardCodeJpaEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
