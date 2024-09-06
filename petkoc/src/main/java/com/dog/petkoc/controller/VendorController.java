package com.dog.petkoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @GetMapping("/profile")
    @ResponseBody
    public String profile() {
        return "ROLE_VENDOR 만 접근 가능한 페이지 입니다.";
    }
}
