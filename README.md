## 링크 -> [http:/bakelab.jshlab.store)

## 스토리
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0002.jpg)

## 개발인원 및 역할

- **성환**: 
  - DB 관계도 설계
  - 프로젝트 기본 틀 제작
  - 관리자 계정 기능(상품 등록,수정, 총계) 
  - 장바구니 기능
  - 결제 기능(카카오 페이)
  - 검색 기능

- **민정**: 
  - 회원가입
  - 카카오 로그인
  
- **경민**: 
  - 마이페이지
  - 리뷰 기능
  
- **정민**: 
  - 기획
  - 디자인
  - 프론트 엔드

## 개발기간
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0007.jpg)

## 사용한 기술
- HTML
- CSS
- JavaScript
- JAVA
- Spring Boot
- MyBatis

### 데이터베이스
- MySQL

### 템플릿 엔진
- Thymeleaf

## FLOW 및 FUNCTION
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0003.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0004.jpg)

## ERD
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/BakeLab_ERD.png)

## 메인 페이지
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0008.jpg)
## 로그인 및 회원가입
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0009.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0020.jpg)

## 유저 MyPage
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0010.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0011.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0012.jpg)
## 상품 상세 정보 및 리뷰
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0013.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0015.jpg)
## 장바구니
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0014.jpg)
## 결제
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0016.jpg)
## 상품 등록 및 수정
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0020.jpg)
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0021.jpg)
## 상품 통계
![이미지](https://github.com/BlueDestinyUnit/BlueDestinyUnit/blob/main/BakeLab/0018.jpg)

### 23.10.6 마감 당시 개선해야할 점
- 판매자 계정에 대한 UI 보완이 필요
- 검색 기능에 일부 버그
- 사이드바 메뉴 호버만 되어야하나 눌리는 현상
- 회원은 장바구니 기능을 정상적으로 사용할 수 있지만, 비회원은 아직 장바구니 기능에 버그가 있음
- copyright를 표시하는 푸터가 없음
- 메뉴바 UI를 개선해야함.

### 현재 개선된 점
- 판매자 계정에 대한 UI 보완
- 어떤 페이지에서도 검색 가능하도록 조치
- 비회원 장바구니 사용 가능하도록 조치 (단 바로 로그인시 비로그인과 다르므로 내역은 저장되지 않음)
- 일관적이지 못했던 메뉴바 개선
- footer 추가
  
### 특이 사항
- 회원가입시 휴대폰 인증 api는 유료 서비스 임의로 브라우저 상 개발자 도구에서 인증번호가 나오도록 하였습니다.
- 카카오 결제는 테스트임으로 실제 결제가 이루어지지는 않습니다.
