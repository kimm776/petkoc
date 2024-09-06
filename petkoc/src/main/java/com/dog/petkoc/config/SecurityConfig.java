package com.dog.petkoc.config;

import com.dog.petkoc.security.EmailUserService;
import com.dog.petkoc.security.ExceptionHandler;
import com.dog.petkoc.security.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final EmailUserService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2UserService oAuth2UserService;
    private final ExceptionHandler exceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/vendor/**").hasRole("VENDOR")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login/sign-in")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            log.debug("소셜 로그인 성공...");
                        })
                        .failureHandler((request, response, e) -> {
                            log.error(e.getMessage());
                        })
                        .defaultSuccessUrl("/")
                        .failureUrl("/login/sign-in")
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login/sign-in")
                        .loginProcessingUrl("/login/email-sign-in")
                        .successHandler(((request, response, authentication) -> {
                            log.debug("이메일 로그인 성공... {}", authentication.getPrincipal().toString());
                        }))
                        .failureHandler(((request, response, exception) -> {
                            log.error(exception.getMessage());
                        }))
                        .defaultSuccessUrl("/")
                        .failureUrl("/login/sign-in")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(exceptionHandler)
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return auth.build();
    }


}
