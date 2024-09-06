package com.dog.petkoc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    /**
     * 인증 필요 url 테스트
     * @return
     */
    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("ROLE_MEMBER 만 접근 가능한 페이지 입니다.");
    }

}
