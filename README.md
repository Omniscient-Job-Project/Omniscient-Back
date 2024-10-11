# 🚀 전지적 구직자 시점(전직자) - 백엔드

<p align="center">
  <strong>혁신적인 채용정보 사이트로, <br>구직자들에게 정확하고 신뢰할 수 있는 채용 정보와 자격증 관련 정보를 제공합니다.</strong>
</p>

<div align="center">
  <h3>혁신적인 채용정보 플랫폼의 강력한 백엔드 시스템</h3>

  [🏗 아키텍처](#-아키텍처) •
  [🛠 기술 스택](#-기술-스택) •
  [💡 주요 기능](#-주요-기능) •
  [📚 API 문서](#-api-문서) •
  [💾 데이터베이스](#-데이터베이스) •
  [🔄 CI/CD 및 배포](#-cicd-및-배포) •
  [⚡ 성능 최적화](#-성능-최적화) •
  [🔒 보안](#-보안) •
  [🧪 테스트](#-테스트)
</div>

<hr>

### 주요 컴포넌트 설명:
- **인증 서비스**: JWT 기반 사용자 인증 및 권한 관리.
- **채용정보 서비스**: 실시간 채용 정보 수집, 처리, 제공.
- **자격증 서비스**: 자격증 정보 관리 및 연관 채용정보 매칭.
- **사용자 서비스**: 사용자 프로필, 선호도, 활동 이력 관리.

## 🛠 기술 스택

### 백엔드 프레임워크 및 언어
- 🖥 **Spring Boot** (2.7.x): 주 애플리케이션 프레임워크
- ☕ **Java** (17): 주 프로그래밍 언어

### 데이터베이스 및 캐싱
- 💾 **MySQL** (8.0): 주 관계형 데이터베이스

### ORM 및 데이터 접근
- 🔗 **Spring Data JPA**: 객체-관계 매핑

### 검색 엔진
- 🔎 **Elasticsearch**: 고성능 전문 검색 및 분석 (예정)

### 보안
- 🔐 **Spring Security**: 인증 및 권한 관리
- 🔑 **JSON Web Token (JWT)**: 토큰 기반 인증

### 빌드 및 패키징
- 🏗 **Gradle**: 빌드 자동화 및 의존성 관리
- 🐳 **Docker**: 애플리케이션 컨테이너화

### CI/CD
- 🔄 **Jenkins**: 지속적 통합 및 배포

## 💡 주요 기능

1. **🔄 실시간 채용 정보 동기화**
   - 다양한 채용 사이트 API 연동 (서울시, 잡아바 등)
   - 중복 데이터 제거 및 정보 정규화 프로세스

2. **🔍 고성능 검색 엔진(예정)**
   - Elasticsearch를 활용한 전문 검색 기능 구현
   - 자동완성, 오타 교정, 동의어 처리 기능 제공
   - 검색 결과 랭킹 알고리즘 구현 (관련성, 최신성, 인기도 고려)
   - 필터링 및 패싯 검색 지원 (지역, 연봉, 경력, 기업 규모 등)

3. **📊 데이터 분석 파이프라인(예정)**
   - 채용 시장 트렌드 분석 리포트 생성
   - 사용자 행동 패턴 분석 및 인사이트 도출

## 📚 API 문서

Swagger UI를 통해 API 문서를 제공합니다. 서버 실행 후 `http://localhost:8080/swagger-ui/index.html`에서 확인 가능합니다.

주요 엔드포인트:
- 🏢 `/api/v1/jobs`: 채용 정보 관련 API
- 🏅 `/api/v1/certifications`: 자격증 정보 관련 API
- 👤 `/api/v1/users`: 사용자 관리 API
- 🔐 `/api/v1/auth`: 인증 관련 API

## 🔄 CI/CD 및 배포

전직자 프로젝트는 Docker와 Jenkins를 활용한 강력한 CI/CD 파이프라인을 구축하여 지속적인 통합과 배포를 실현합니다.

### 배포 프로세스
1. 개발자가 GitHub 저장소에 코드를 push합니다.
2. Jenkins가 변경을 감지하고 파이프라인을 트리거합니다.
3. 코드를 체크아웃하고 Gradle을 사용하여 빌드합니다.
4. 통합 테스트를 실행합니다.
5. Docker 이미지를 빌드하고 레지스트리에 푸시합니다.

## 🔒 보안

- 🔐 JWT 기반 인증 구현
- 🌐 CORS 설정을 통한 리소스 접근 제어

## 🧪 테스트

- ✅ JUnit 5를 활용한 단위 테스트
- 🔄 Mockito를 이용한 Mock 객체 활용
- 🐳 통합 테스트를 위한 TestContainers 활용
- 🔄 CI/CD 파이프라인에 테스트 자동화 통합

<hr>

<div align="center">
  <img src="https://img.shields.io/badge/SPRING-00CBC6?style=for-the-badge&logo=SPRING&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/SPRING_BOOT-F0047F?style=for-the-badge&logo=SPRING BOOT&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/SPRING_SECURITY-0288D1?style=for-the-badge&logo=SPRING SECURITY&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/SPRING_DATA_JPA-F58025?style=for-the-badge&logo=SPRING DATA JPA&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/JSON_WEB_TOKEN-FFF000?style=for-the-badge&logo=JSON WEB TOKEN&logoColor=white" style="display: inline-block; margin: 5px;">
</div>

<div align="center">
  <img src="https://img.shields.io/badge/MYSQL-F58025?style=for-the-badge&logo=MYSQL&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/DOCKER-F58025?style=for-the-badge&logo=DOCKER&logoColor=white" style="display: inline-block; margin: 5px;">
  <img src="https://img.shields.io/badge/JENKINS-006600?style=for-the-badge&logo=JENKINS&logoColor=white" style="display: inline-block; margin: 5px;">
</div>

<div align="center">
  <sub>Built with ❤️ by the Omniscient Job Project Team</sub>
</div>

<div align="center">
  <h3>🌟 전지적 구직자 시점(전직자) 프로젝트에 기여해주셔서 <br>감사합니다! 🌟</h3>
  <p>문의사항이나 제안이 있으시면 언제든 연락 주세요.</p>
  <a href="mailto:project@example.com">📧 이메일 문의</a> •
  <a href="https://github.com/your-username/your-repo/issues">🐛 이슈 리포트</a>
</div>
