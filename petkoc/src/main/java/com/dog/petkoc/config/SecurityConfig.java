package com.dog.petkoc.config;

import com.dog.petkoc.security.CustomUserService;
import com.dog.petkoc.security.ExceptionHandler;
import com.dog.petkoc.security.JwtFilter;
import com.dog.petkoc.security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandler exceptionHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final JwtFilter jwtFilter;
    private final CustomUserService customUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/test").hasRole("MEMBER")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/sign-in")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customUserService)
                        )
                        .successHandler(loginSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            log.error("소셜 로그인 실패: {}", exception.getMessage());
                            response.sendRedirect("/login/sign-in?error=true");
                        })
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login/sign-in")
                        .loginProcessingUrl("/login/email-sign-in")
                        .successHandler(loginSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            log.error("이메일 로그인 실패: {}", exception.getMessage());
                            response.sendRedirect("/login/sign-in?error=true");
                        })
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(exceptionHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder);
        return auth.build();
    }


}
