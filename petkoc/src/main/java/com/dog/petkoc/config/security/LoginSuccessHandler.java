package com.dog.petkoc.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(userPrincipal.getMember());
        SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));

        String rememberMe = request.getParameter("rememberMe");
        long maxAgeSeconds;

        // rememberMe에 체크했을 경우 1달간 유지, 아닐 경우 세션 유지
        if ("on".equals(rememberMe)) {
            // JWT 토큰 만료 시간 계산 (현재 시간과 만료 시간의 차이)
            Date expirationDate = jwtTokenProvider.getClaims(token).getExpiration();
            maxAgeSeconds = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;  // 만료 시간까지 남은 시간 (초 단위)
        } else {
            maxAgeSeconds = -1;
        }

        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)  // HttpOnly 설정
                .secure(true)    // Secure 설정 (HTTPS에서만 전송)
                .path("/")       // 쿠키의 유효 경로
                .maxAge(maxAgeSeconds) // 쿠키 만료 시간 (초 단위)
                .sameSite("Strict")  // SameSite 옵션 설정 (CSRF 방지)
                .build();

        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        // 테스트 페이지로 전송 TODO: 사용자가 접근 시도했던 페이지로 이동
        response.sendRedirect(request.getContextPath() + "test");

        log.debug("{} 로그인 성공...", userPrincipal.getProvider());
    }
}
