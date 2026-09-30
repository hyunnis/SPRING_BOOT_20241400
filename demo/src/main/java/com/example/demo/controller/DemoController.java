package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동


@Controller // 컨트롤러 어노테이션 명시
public class DemoController {

    @Autowired 
    TestService testService; // DemoController 클래스 아래 객체 주입

    @GetMapping("/hello") // 전송 방식 get
    public String hello(Model model) {
        model.addAttribute("data", "서현"); //model 설정
        model.addAttribute("greeting", "반갑습니다");
        model.addAttribute("day", "오늘");
        model.addAttribute("weatherLabel", "날씨는");
        model.addAttribute("weatherValue", "매우 좋습니다~");
        return "hello"; // hello.html 연결
    }

// 하단에 맵핑 이어서 추가
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("이서현");
        model.addAttribute("data4",test);
        System.out.println("데이터 출력 디버그 : " + test);
        return "testdb";
    }
}