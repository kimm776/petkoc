package com.dog.petkoc.member.login.presentation.controller;

import com.dog.petkoc.member.login.application.entity.Member;
import com.dog.petkoc.member.login.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/sign-in")
    public String signInForm() {
        return "login/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpForm() {
        return "login/sign-up";
    }

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

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Member Role 접근 가능";
    }

}
