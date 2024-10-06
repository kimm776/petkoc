package com.dog.petkoc.example.presentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "예시 API", description = "react 연동 및 Swagger 예시입니다.")
public class HelloController {
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
