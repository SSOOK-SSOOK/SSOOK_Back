# 🌱 SSOOK-SSOOK (쑥쑥) - Backend

**주니어 개발자와 개발자 지망생을 위한 개발 쇼츠(Shorts) 교육 플랫폼, 쑥쑥(SSOOK-SSOOK)의 백엔드 저장소입니다.**

## 📖 프로젝트 소개
**쑥쑥**은 짧고 핵심적인 영상을 통해 개발 지식을 쉽고 빠르게 습득할 수 있도록 돕는 플랫폼입니다.
<br>
방대한 강의보다는 **핵심 요약(Shorts)** 중심의 콘텐츠를 제공하여, 학습 부담을 줄이고 접근성을 높이는 것을 목표로 합니다.

## 🛠 Tech Stack

| 구분 | 기술 및 버전 | 비고 |
| --- | --- | --- |
| **Language** | Java 17 | LTS Version |
| **Framework** | Spring Boot 3.5.8 | |
| **Build Tool** | Maven | |
| **Database** | MySQL | 8.0+ 권장 |
| **ORM / SQL Mapper** | MyBatis 3.0.5 | |
| **VCS** | Git, GitHub | |

## 🚀 Getting Started (시작하기)

프로젝트를 로컬 환경에서 실행하기 위한 가이드입니다.

### 1. Prerequisites (사전 준비)
* JDK 17 이상 설치
* MySQL 서버 실행
* Git 설치

### 2. Clone Repository
```bash
git clone [https://github.com/SSOOK-SSOOK/SSOOK_Back.git](https://github.com/SSOOK-SSOOK/SSOOK_Back.git)
cd SSOOK_Back
```

### 3. Configuration (환경 설정)
`src/main/resources/application.yml` (또는 `properties`) 파일을 생성하고 데이터베이스 정보를 설정해야 합니다.
*(보안을 위해 이 파일은 git에 커밋되지 않도록 주의해주세요)*

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ssook_db?serverTimezone=UTC&characterEncoding=UTF-8
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

  mybatis:
    mapper-locations: classpath:mapper/*.xml
    type-aliases-package: com.ssook.backend.domain  # 패키지명에 맞게 수정 필요
```

### 4. Build & Run
프로젝트 루트 경로에서 다음 명령어를 실행합니다.

**Mac/Linux:**
```bash
./mvnw clean package
java -jar target/ssook-backend-0.0.1-SNAPSHOT.jar
```

**Windows:**
```cmd
mvnw.cmd clean package
java -jar target/ssook-backend-0.0.1-SNAPSHOT.jar
```

## 🤝 Collaboration Rules (협업 규칙)

### Branch Strategy
* **main**: 배포 가능한 안정 버전
* **develop**: 다음 배포를 위한 개발 진행 브랜치
* **feature/기능명**: 각 기능 단위 개발 브랜치 (ex: `feature/login`, `feature/video-upload`)

### Commit Convention
* `feat`: 새로운 기능 추가
* `fix`: 버그 수정
* `docs`: 문서 수정
* `refactor`: 코드 리팩토링 (기능 변경 없음)
* `chore`: 빌드 테스트 업데이트, 패키지 매니저 설정 등
