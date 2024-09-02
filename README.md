# zl존 딜리버리
각종 상품의 주문 관리 플랫폼

# 팀원 역할분담
- [상우](https://github.com/lswoo0705): Category, Order, Payment 담당
- [성원](https://github.com/choi5798): User, Address 담당
- [유빈](https://github.com/asd42270): Store, Product, Chatbot 담당

# 서비스 구성 및 실행방법
1. git clone 또는 코드를 다운로드
2. profile 설정, jwt secret key, gpt api key 주입 (별도 문의)
3. ZlzoneDeliveryApplication.java 의 main 메소드 실행

# 프로젝트 목적/상세
## 목적
음식점들의 배달 및 포장 주문 관리, 결제, 주문 내역 관리 기능을 제공

## 상세
회원 관리부터 음식점과 관련된 주문, 결제, 그리고 배달까지의 과정을 한 곳에서 해결할 수 있도록 만든 플랫폼입니다

# ERD
![image](https://github.com/user-attachments/assets/ae862e16-507c-4cab-8f17-56279bed1618)

# 기술 스택
- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- Spring Security
- PostgreSQL (+MySQL)
- JWT
- Redis
- Spring AI

# 인프라 설계도
![image](https://github.com/user-attachments/assets/ed5b09de-3d60-4f1b-a832-0a133d6a72ed)
