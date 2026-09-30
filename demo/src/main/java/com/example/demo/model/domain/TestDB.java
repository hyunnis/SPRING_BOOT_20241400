package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity // testDB라는 객체와 DB 테이블을 매핑. JAP가 관리
@Table(name = "textdb") // 테이블 이름 : testdb
@Data // set/get/tostring 등 필수 메서드 자동 생성

public class TestDB {
    @Id // 해당 변수가 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 할당
    private Long id;

    @Column (nullable = true)
    private  String name;
}