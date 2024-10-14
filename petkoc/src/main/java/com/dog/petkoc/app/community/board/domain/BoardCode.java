package com.dog.petkoc.app.community.board.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BoardCode {
    private Long id;
    private String name;
    private String description;
}
