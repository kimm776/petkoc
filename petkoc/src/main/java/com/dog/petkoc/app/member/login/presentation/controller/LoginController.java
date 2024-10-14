package com.dog.petkoc.app.member.login.presentation.controller;

import com.dog.petkoc.app.member.login.application.entity.Member;
import com.dog.petkoc.app.member.login.application.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Tag(name = "로그인 API", description = "로그인 API입니다.")
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    /**
     * 로그인 페이지 리턴
     * @return
     */
    @GetMapping("/sign-in")
    public String signInForm() {
        return "login/sign-in";
    }

    /**
     * 회원가입 페이지 리턴
     * @return
     */
    @GetMapping("/sign-up")
    public String signUpForm() {
        return "login/sign-up";
    }

    /**
     * 이메일 회원가입 처리
     * @param member
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/email-sign-up")
    public String emailSignUp(@ModelAttribute Member member,
                              RedirectAttributes redirectAttributes) {
        try {
            member.setProvider("Email");
            memberService.registerMember(member);
            return "redirect:/login/sign-in";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입에 실패했습니다: " + e.getMessage());
            return "redirect:/login/sign-up";
        }
    }

    /**
     * 롤 테스트
     * @return
     */
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Member Role 접근 가능";
    }

}
