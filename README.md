<div align="center">
<br><br>

## 자바웹프로그래밍(2) 수업 실습 정리 💘
> 학과 : 미디어소프트웨어학과
> 학번 : 20241400
> 이름 : 이서현

</div>
---

## project introduce 
자바웹프로그래밍(2) 수업으로, 전반적으로 포트폴리오 웹 사이트 제작을 하였습니다.

---
# 💗 Weekly Study Log

## 2주차 수업 내용 (1주차는 OT 진행)
### 개발 환경 및 스트링 부트 준비
- Extension Pack for Java, Spring Boot Extension Pack 설치
- Spring Initializr: Create a Maven Project...
- S/W 모듈 선택하여 폴더 생성 후 지정 
<div align ="center">
    <img width="50%" src="./demo/projectimg/2주차_1.png">
</div>
<br>

### 자바 클래스 파일
- DemoApplication을 열어 어노테이션을 통해 자동 설정
<div align ="center">
    <img width="50%" src="./demo/projectimg/2주차_2.png">
</div>
<br>
- localhost:8080을 실행하여 웹사이트 open
<br>

### Thymeleaf?
- HTML 기반 템플릿 엔진으로, html lang="en" xmlns:th="http://www.thymeleaf.org" 으로 선언한다.
- 메인화면 index.html의 위치 
<br>

### DemoController 내용 작성
<div align ="center">
    <img width="50%" src="./demo/projectimg/2주차_3.png">
</div>
<br>


## 3주차 수업
### 포트폴리오 웹 사이트 제작
- 기존 index.html 사본 복사 후 교수님이 올려주신 파일을 다운받아 index,html으로 덮어씌운다.
- resource > static 폴더 생성, css/js/images/fonts 전체를 옮긴다.
- 최상단 선언에 내부 컨테이너에서 리턴시 thymeleal을 데이터 출력시 반드시 상단에 아래 코드를 표기한다.
- html xmlns:th="http://www.thymeleaf.org"
- URL 출력 방식? @{...} 컨텍스트 경로를 자동으로 반영함
<div align ="center">
    <img width="50%" src="./demo/projectimg/3주차_1.png">
</div>
<br>

## 4주차 수업
### 데이터베이스 연동 (온라인 수업)
- pom.xml에서 주석 처리 했던 부분을 다시 해제한다.
<div align ="center">
    <img width="50%" src="./demo/projectimg/4주차_1.png">
</div>
<br>
- 데이터베이스 연동으로 MYSQL을 설치 및 연결한다
- 저는 맥 사용자라 Docker 설치 후 터미널로 Mysql 설치 및 연동 진행하였습니다.
- create database spring; / flush privileges; 설치하여 데이터 베이스 생성
- 확장 : mysql 검색 후에 설치. 데이터 베이스 이름 : spring으로 설정함
<div align ="center">
    <img width="50%" src="./demo/projectimg/4주차_2.png">
</div>
<div align ="center">
    <img width="50%" src="./demo/projectimg/4주차_3.png">
</div>
<br>

### 구조 변경
- 단일 폴더에서 패키지 세분화로 분리하기 위해 내부 구조 변경
- M : model or domain 폴더
- V : view, 기존 templates 폴더
- C : controller 폴더
- Service : service 폴더 
- Repository : repository 폴더
- 계층 별로 controller / model > service, repository, domain 폴더 총 5개 생성
<div align ="center">
    <img width="50%" src="./demo/projectimg/4주차_4.png">
</div>
<br>

## 5주차 수업
### 의존성 모듈 추가
- pom.xml 파일 열어서 의존성 추가한다.
- spring-boot-starter-security : 로그인과 보안을 담당하는 기능 삽입
- admin : 관리자만 접근 가능 / mypage : 로그인한 사용자만 접근 가능
- thymeleaf-extras-springsecurity6 : Thymeleaf HTML에서 로그인/권한 정보를 활용하여 접근
<div align ="center">
    <img width="50%" src="./demo/projectimg/5주차_1.png">
</div>
<br>

### 보안 설정 - SecurityConfig
<div align ="center">
    <img width="50%" src="./demo/projectimg/5주차_2.png">
</div>
<br>
- config > SecurityConfig 생성
- @Bean : 가입·로그인에서 암호 알고리즘 : 같은 BCrypt 사용
- permitAll() :누구나 접근 가능하게 (메인, 상세, 로그인, 가입)
- defaultSuccessUrl : 성공 시 메인 index 화면
- failureUrl : 실패 시 에러 파라미터

### 요청 페이지 - 컨트롤러
- controller > MemberController 생성
- 메인 우측 상단에 로그인 버튼 확인
<div align ="center">
    <img width="50%" src="./demo/projectimg/5주차_3.png">
</div>
<br>

### 회원 리포지토리 생성
- repository > MemberRepository 생성
- findByUsername : 

### 로그인 연결 - UserDetailsService
- 시큐리티에 회원 조회 방법 제공
- 회원가입 후 로그인하면 오른쪽 상단에 "로그아웃" 칸으로 바뀜
<div align ="center">
    <img width="50%" src="./demo/projectimg/5주차_5.png">
</div>
<br>
- 로그아웃시, 로그아웃 되었다는 화면 나타남
<div align ="center">
    <img width="50%" src="./demo/projectimg/5주차_6.png">
</div>
<br>


## 7주차 수업
### 내 정보 API
<div align ="center">
    <img width="50%" src="./demo/projectimg/7주차_2.png">
</div>
<br>
- 로그인하고 /api/me 들어가면 비밀번호(해시)가 그대로 보임
- 해결 방법 : 응답용 객체를 따로 해서 응답 DTO를 만드는 것!(비밀번호 제외)