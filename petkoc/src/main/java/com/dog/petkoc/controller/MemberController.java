package com.dog.petkoc.controller;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.repository.MemberRepository;
import com.dog.petkoc.service.MemberService;
import com.dog.petkoc.service.NaverOauth2Service;
import com.dog.petkoc.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final NaverOauth2Service naverOauth2Service;


    /**
     * 회원 등록
     * @param email
     * @param password
     * @param fullName
     * @return
     */
    @PostMapping("/member/register")
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
    @GetMapping("/member/profile")
    @ResponseBody
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("This is a protected resource.");
    }

    @GetMapping("/test")
    @ResponseBody
    public Member test() {
        return memberService.findByEmail("test@email.com");
    }

    @GetMapping("/login/loadPage")
    public String loginPage(Model model) {
//        model.addAttribute("naverLoginUrl", naverOauth2Service.getAuthorizeUri());
        return "member/login";
    }


}
