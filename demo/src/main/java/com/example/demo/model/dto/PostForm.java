package com.example.demo.model.dto;

import lombok.Data;

@Data
public class PostForm { // 글쓰기 및 수정 화면 -> 컨트롤러로 전달되는 데이터(DTO)
    private String title; // 제목
    private String content; // 내용
    // 작성자는 받지 않는ㄷ -> 로그인한 사람으로 서버가 정한다
    
}
