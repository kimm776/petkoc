package com.dog.petkoc.community.board.application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "boardCode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCode {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description")
    private String description;

}
