package com.dog.petkoc.service;

import com.dog.petkoc.util.SessionUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverOauth2Service implements Oauth2Service {

    private final SessionUtil sessionUtil;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String NAVER_AUTHORIZATION_URI;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String NAVER_REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String NAVER_TOKEN_URI;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;


    @Override
    public String getAuthorizeUri() {
        return NAVER_AUTHORIZATION_URI + "?client_id=" + NAVER_CLIENT_ID +
                "&redirect_uri=" + NAVER_REDIRECT_URI +
                "&state=" + sessionUtil.generateState() +
                "&response_type=code";
    }

    @Override
    public void getAccessToken(String code, String state) {
        String url = NAVER_TOKEN_URI + "?grant_type=authorization_code"
                + "&client_id=" + NAVER_CLIENT_ID
                + "&client_secret=" + NAVER_CLIENT_SECRET
                + "&state=" + state
                + "&code=" + code;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        // JSON 응답에서 access_token, refresh_token, expires_in 추출
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String accessToken = rootNode.path("access_token").asText();
            String refreshToken = rootNode.path("refresh_token").asText();
            int expiresIn = rootNode.path("expires_in").asInt();

            log.debug("Access Token: {}", accessToken);
            log.debug("Refresh Token: {}", refreshToken);
            log.debug("Expires In: {}", expiresIn);

            this.getUserInfo(accessToken);
            // TODO: 회원가입
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to parse token response", e);
        }
    }

    @Override
    public void refreshAccessToken() {
        //TODO: Refresh token 로직
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) {
        //TODO: user 이메일, 이름 리턴
        Map<String, String> userInfo = new HashMap<>();
        return userInfo;
    }
}
