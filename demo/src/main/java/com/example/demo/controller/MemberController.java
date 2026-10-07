package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.service.MemberService;
import com.example.demo.model.dto.MemberForm;


@Controller 
public class MemberController {

    @Autowired
    MemberService memberService; // 클래스 상단에 객체 주입

    @GetMapping("/login") // 로그인 화면 (post/login은 시큐리티가 처리)
    public String login() {
        return "login"; //login.html 연결
        
    }
    
    @GetMapping("/signup") //회원가입 화면
    public String signupForm() {
        return "signup"; // signup.html 연결
    }
    @PostMapping("/signup")
    public String signup(MemberForm memberForm, Model model) {
        try {
            memberService.signup(memberForm);
        } catch(IllegalArgumentException e) { // 중복 아이디 등
            model.addAttribute("error", e.getMessage()); //  ${error}
            return "signup"; // 입력값 유지한 채 다시 가입 화면
        }
        return "redirect:/login?signup"; // 성공 → 로그인 화면
    }
}
