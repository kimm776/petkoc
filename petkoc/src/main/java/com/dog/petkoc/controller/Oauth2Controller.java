package com.dog.petkoc.controller;

import com.dog.petkoc.service.NaverOauth2Service;
import com.dog.petkoc.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final SessionUtil sessionUtil;
    private final NaverOauth2Service naverOauth2Service;

    @GetMapping("/code/naver")
    @ResponseBody
    public String authNaver(@RequestParam("code") String code, @RequestParam("state") String state) {
        if (state.equals(sessionUtil.getAttribute("state"))) {
            naverOauth2Service.getAccessToken(code, state);
            return "success";
        } else {
            return "fail";
        }
    }
}
