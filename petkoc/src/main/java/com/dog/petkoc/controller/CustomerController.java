package com.dog.petkoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/profile")
    @ResponseBody
    public String profile() {
        return "ROLE_CUSTOMER 만 접근할 수 있는 페이지 입니다.";
    }
}
