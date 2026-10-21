
package com.example.demo.controller;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.dto.MemberResponse;
import com.example.demo.model.service.MemberService;


@RestController // 화면(HTML) 대신 데이터(JSON)를 응답하는 컨트롤러
@RequestMapping("/api") // 이 컨트롤러의 모든 URL 앞에 /api

public class MemberApiController {
    @Autowired
    MemberService memberService;

    @GetMapping("/hello") // 첫 번째 API : GET /api/hello (누구나)
    public Map<String, Object> hello() {
        // Map 을 반환하면 스프링이 JSON 으로 바꿔 준다
        return Map.of("message", "안녕하세요, REST API!",
                    "time", LocalDateTime.now().withNano(0));
    }

    @GetMapping("/me") // 내 정보 api : get/api/me (로그인 필요)
    public MemberResponse me(Principal principal) { // 반환형 Member -> MemberResponse
        Member member = memberService.findByUsername(principal.getName());
        return MemberResponse.from(member); // 엔티티를 대신 DTO 반환
    }

    @PostMapping("/admin/members") // 회원 등록 : POST /api (본문 JSON → MemberForm)
    public ResponseEntity<?> create(@RequestBody MemberForm form) {
        try {
            Member saved = memberService.signup(form); // 5주차 회원가입 로직 재사용 (BCrypt)
            return ResponseEntity
                       .created(URI.create("/api/admin/members/" + saved.getId())) // 201 + Location
                       .body(MemberResponse.from(saved));
        } catch (IllegalArgumentException e) { // 중복 아이디, 빈 값…
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // 400
        }
    }
}
