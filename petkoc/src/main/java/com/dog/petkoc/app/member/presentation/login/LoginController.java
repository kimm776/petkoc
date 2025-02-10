package com.dog.petkoc.app.member.presentation.login;

import com.dog.petkoc.app.member.domain.entity.Member;
import com.dog.petkoc.app.member.application.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
    public String test() {
        return "login/test";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/sign-out")
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // SecurityContext 초기화
        SecurityContextHolder.clearContext();

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // JWT 쿠키 삭제 (유효기간을 0으로 설정)
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키 즉시 삭제
        response.addCookie(cookie);

        // login 페이지로 리다이렉트
        response.sendRedirect("/login/sign-in");
    }
}
