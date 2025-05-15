# 🛡️ User Management System

사용자 회원가입, 로그인(JWT), 권한 제어, 사용자 관리 기능을 제공하는 Spring Boot 기반의 사용자 관리 시스템입니다. Swagger를 통한 문서화 및 인메모리 저장소 기반으로 구성되어 있으며, 인증/인가 테스트 및 예외 처리 로직이 포함되어 있습니다.

---

## 🛠 사용 기술 스택

* Java 17
* Spring Boot 3.4.5
* Spring Security + JWT
* springdoc-openapi (Swagger)
* Gradle
* JUnit5

---

## 🚀 실행 방법

### 1. 프로젝트 클론

```bash
git clone https://github.com/hyeririjeon/sprata-assignment.git
cd sprata-assignment/UserManagementSystem
```

### 2. 환경 변수 설정

`application.properties`에서 JWT 시크릿 키 설정을 확인합니다. 예시:

```properties
jwt.secret.key=your-base64-secret-key
```

### 3. 프로젝트 빌드 및 실행

```bash
# 1. Gradle 빌드
./gradlew build

# 2. JAR 파일 실행
java -jar build/libs/UserManagementSystem-0.0.1-SNAPSHOT.jar
```

---

## 📖 Swagger 문서 접속
[Swagger PDF](./docs/Swagger%20UI.pdf)
```text
Local 실행 시: http://localhost:8080/swagger-ui/index.html
배포 링크: http://3.34.200.144:8080/swagger-ui/index.html 
```

Swagger UI에서 전체 API 명세, 요청/응답 예시 및 상태 코드를 확인할 수 있습니다.

---

## 📌 주요 기능

* 회원가입 (중복 ID 예외 처리)
* 로그인 (JWT 발급)
* 내 정보 조회
* MASTER → ADMIN 권한 부여
* 사용자 전체 조회 (ADMIN, MASTER)
* 사용자 정지 처리 (ADMIN, MASTER)
* 정지된 사용자 접근 제한

---

## 📒 API 명세 (요약)

**Base URL**: http://3.34.200.144:8080
- 예시 : http://3.34.200.144:8080/signup, http://3.34.200.144:8080/login

| 메서드   | 경로                              | 설명           | 권한            |
| ----- | ------------------------------- | ------------ | ------------- |
| POST  | `/signup`                       | 회원가입         | 모두            |
| POST  | `/login`                        | 로그인 (JWT 발급) | 모두            |
| GET   | `/myInfo`                       | 내 정보 조회      | USER 이상       |
| GET   | `/admin/users`                  | 전체 사용자 조회    | ADMIN, MASTER |
| PATCH | `/admin/users/{username}/roles` | 관리자 권한 부여    | MASTER        |
| PATCH | `/admin/users/{username}/ban`   | 사용자 정지       | ADMIN, MASTER |

> 상세 응답 예시 및 상태 코드는 Swagger UI에서 확인 가능

---

## 🧪 테스트 실행

```bash
./gradlew test
```

### 테스트 항목

* 회원가입 성공/실패
* 로그인 성공/실패 
* 관리자 권한 부여 
* 사용자 정지 
* 내 정보 조회
* 사용자 전체 조회
* 권한별 API 접근
* JWT 토큰 생성 및 검증

---

## 🗂 프로젝트 구조

```
📂 src
 ├── main/java/com/study/usermanagementsystem
 │   ├── common            # 공통 설정 (예외, 응답코드, JWT, 필터)
 │   ├── controller        # API 컨트롤러
 │   ├── domain            # 사용자 도메인 모델
 │   ├── dto               # 요청/응답 DTO
 │   ├── repository        # In-memory 저장소
 │   ├── service           # 서비스 로직
 ├── test/java/com/study/usermanagementsystem
 │   ├── controller        # API 테스트
 │   ├── service           # 서비스 단위 테스트
 ├── resources
 │   ├── application.properties # 설정파일
 ├── build.gradle          # Gradle 빌드 파일
 ├── README.md             # 프로젝트 문서
```
