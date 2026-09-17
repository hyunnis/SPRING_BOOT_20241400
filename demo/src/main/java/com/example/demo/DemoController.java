package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller // 컨트롤러 어노테이션 명시
public class DemoController {
    @GetMapping("/hello") // 전송 방식 get
    public String hello(Model model) {
        model.addAttribute("data", "서현"); //model 설정
        model.addAttribute("greeting", "반갑습니다");
        model.addAttribute("day", "오늘");
        model.addAttribute("weatherLabel", "날씨는");
        model.addAttribute("weatherValue", "매우 좋습니다~");
        return "hello"; // hello.html 연결
    }
}