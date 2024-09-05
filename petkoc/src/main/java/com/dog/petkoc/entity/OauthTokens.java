package com.dog.petkoc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter@Setter
@Table(name = "oauth_tokens")
public class OauthTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID memberId;

    @Column(unique = true, nullable = false)
    private String accessToken;

    @Column(unique = true, nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Long accessTokenExpiresAt;

    @Column(nullable = false)
    private Long refreshTokenExpiresAt;

    @Column(nullable = false)
    private String provider;
}
