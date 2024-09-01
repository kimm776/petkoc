package com.dog.petkoc.controller;

import com.dog.petkoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     * @param email
     * @param password
     * @param fullName
     * @return
     */
    @PostMapping("/register")
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
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("This is a protected resource.");
    }

}
