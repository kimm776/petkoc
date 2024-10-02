package com.dog.petkoc.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        response.setHeader("Authorization", "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"status\": \"success\", \"token\": \"" + token + "\"}");
        response.getWriter().flush();
        log.debug("{} 로그인 성공...", userPrincipal.getProvider());
    }
}
