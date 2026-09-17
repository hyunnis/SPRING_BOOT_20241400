<div align="center">
<br><br>

## 자바웹프로그래밍(2) 수업 실습 정리 💘
> 학번 : 20241400
> 이름 : 이서현

</div>
---

## project introduce 
자바웹프로그래밍(2) 수업으로, 전반적으로 포트폴리오 웹 사이트 제작을 하였습니다.

---
# 💗 Weekly Study Log

## 2주차 수업 내용
### 개발 환경 및 스트링 부트 준비
- Extension Pack for Java, Spring Boot Extension Pack 설치
- Spring Initializr: Create a Maven Project...
- S/W 모듈 선택하여 폴더 생성 후 지정 
<div align ="center">
    <img width="50%" src="projectimg/2주차_1.png">
</div>
<br>

### 자바 클래스 파일
- DemoApplication을 열어 어노테이션을 통해 자동 설정
<div align ="center">
    <img width="50%" src="projectimg/2주차_2.png">
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
    <img width="50%" src="projectimg/2주차_3.png">
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
    <img width="50%" src="projectimg/3주차_1.png">
</div>
<br>
