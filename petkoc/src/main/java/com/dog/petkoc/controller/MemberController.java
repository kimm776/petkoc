package com.dog.petkoc.controller;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.repository.MemberRepository;
import com.dog.petkoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naver_authorization_uri;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naver_client_id;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naver_redirect_uri;

    /**
     * 회원 등록
     * @param email
     * @param password
     * @param fullName
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerMember (
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "provider", defaultValue = "EMAIL") String provider
    ) {
        try {
            memberService.registerMember(email, password, fullName, provider);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register member.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Member registered successfully.");
    }

    /**
     * 인증 필요 url 테스트
     * @return
     */
    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("This is a protected resource.");
    }

    @GetMapping("/test")
    @ResponseBody
    public Member test() {
        return memberService.findByEmail("test@email.com");
    }

    @GetMapping("/page/login")
    public String loginPage(Model model) {
        String naverLoginUrl = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code", naver_authorization_uri, naver_client_id, naver_redirect_uri);
        model.addAttribute("naverLoginUrl", naverLoginUrl);
        return "member/login";
    }

}
