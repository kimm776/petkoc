package com.dog.petkoc.security;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenExpiresInSeconds;
    private SecretKey key;
    private final CustomUserService customUserService;

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret,
                            @Value("${spring.jwt.token-expires-in-seconds}") Long tokenExpiresInSeconds,
                            CustomUserService customUserService) {
        this.secret = secret;
        this.tokenExpiresInSeconds = tokenExpiresInSeconds;
        this.customUserService = customUserService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = secret.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Member member) {
        Date date  = new Date();
        Date expireDate = new Date(date.getTime() + tokenExpiresInSeconds);
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        headerClaims.put("alg", "HS256");

        String token = Jwts.builder()
                .header().add(headerClaims)
                .and()
                .issuedAt(date)
                .expiration(expireDate)
                .subject(member.getEmail())
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .claim("name", member.getName())
                .claim("provider", member.getProvider())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        log.info("JWT token: {}", token);
        return token;
    }

    public boolean validateToken(String token) {
        log.info("Validate token: {}", token);
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            log.info("Success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
    
    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).get("email").toString();
        UserPrincipal userPrincipal = customUserService.extractUser(email);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }
}
