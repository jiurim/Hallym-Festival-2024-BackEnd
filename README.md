# HallymFestival2023 -Backend-


<p align="center">
  <img src="https://github.com/user-attachments/assets/93c6b7b4-4ab6-4294-8ac4-82d671f656fd" width="45%" />
  <img src="https://github.com/user-attachments/assets/5a9a9baa-c423-4d5e-8952-39d381a4ccd6" width="45%" />
</p>

# Hallym Spring Festival Web Application

>한림대학교 대동제 <br>
>개발기간: 2024.05 ~ 2024.05 <br>
>운영기간: 2024.05.21 ~ 2024.05.23

##

| BE | BE |
| :---: | :---: |
| 김선우 | 지우림 |
| <img width="130px" src="https://avatars.githubusercontent.com/u/114386406?v=4" /> | <img width="130px" src="https://avatars.githubusercontent.com/u/78469127?s=400&u=1cac70dfb6b29f314daee9a28120e6459a31bacd&v=4" /> |
| 한림대학교 | 한림대학교 |
| 빅데이터전공 3학년 | 빅데이터전공 4학년 |
| 될때까지 시도하는 집념의 팀장 | 성공을 위해 묵묵히 개발하는 팀원 |
| [@kimxxunu](https://github.com/Kimxxunu) | [@jiurim](https://github.com/jiurim) |

##

## 📚 기술 스택
- **Development**: <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> 

- **Environment**: <img src="https://img.shields.io/badge/apache%20tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger Badge"> 

- **AWS**: <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white" alt="AWS Badge"> <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white" alt="S3 Badge"> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white" alt="RDS Badge"> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white" alt="EC2 Badge">

- **Tools**: <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"> <img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">

## 📚라이브러리
- Spring Data JPA
- Spring Web
- Lombok
- MySQL Connector
- Spring Boot Configuration Processor
- Springfox
- Spring Cloud AWS
- JSON Web Token (jjwt-api, jjwt-impl, jjwt-jackson)
- Spring Security.

## 🖇️아키텍처
<img src="https://github.com/user-attachments/assets/e317aacc-aa9d-4a97-8cbc-78312ad7eaed"/>

## 🧬 프로젝트 구조

```bash
└── 🗂 main 
    ├── 🗂 java 
    │   └── 🗂 com
    │       └── 🗂 hallymfestival
    │           └── 🗂 hallymfestival
    │               ├── 📑 Application.java
    │               ├── 🗂 domain
    │               │   ├── 🗂 community
    │               │   │   ├── 🗂 controller
    │               │   │   │   ├── 📑 AdminCommunityController.java
    │               │   │   │   └── 📑 CommunityController.java
    │               │   │   ├── 🗂 dto
    │               │   │   │   ├── 📑 CommunityDetailDto.java
    │               │   │   │   ├── 📑 CommunityDto.java
    │               │   │   │   └── 📑 CommunityResponseDto.java
    │               │   │   ├── 🗂 entity
    │               │   │   │   └── 📑 CommunityEntity.java
    │               │   │   ├── 🗂 repository
    │               │   │   │   └── 📑 CommunityRepository.java
    │               │   │   └── 🗂 service
    │               │   │       ├── 📑 CommunityService.java
    │               │   │       └── 📑 CommunityServiceImpl.java
    │               │   ├── 🗂 find
    │               │   │   ├── 🗂 controller
    │               │   │   │   ├── 📑 AdminFindController.java
    │               │   │   │   └── 📑 FindRestController.java
    │               │   │   ├── 🗂 dto
    │               │   │   │   ├── 📑 FindAddRequest.java
    │               │   │   │   └── 📑 FindApiResponse.java
    │               │   │   ├── 🗂 entity
    │               │   │   │   └── 📑 FindEntity.java
    │               │   │   ├── 🗂 domain
    │               │   │   │   └── 📑 FindRepository.java
    │               │   │   └── 🗂 service
    │               │   │       ├── 📑 FindService.java
    │               │   │       ├── 📑 FindServiceImpl.java
    │               │   │       └── 📑 S3Service.java
    │               │   ├── 🗂 manager
    │               │   │   ├── 🗂 controller
    │               │   │   │   ├── 📑 ManagerController.java
    │               │   │   │   └── 📑 WebController.java
    │               │   │   ├── 🗂 dto
    │               │   │   │   ├── 📑 JwtToken.java
    │               │   │   │   ├── 📑 ManagerRequestDto.java
    │               │   │   │   ├── 📑 ManagerResponseDto.java
    │               │   │   │   └── 📑 TokenRequestDto.java
    │               │   │   ├── 🗂 entity
    │               │   │   │   ├── 📑 Authority.java
    │               │   │   │   ├── 📑 Manager.java
    │               │   │   │   └── 📑 RefreshToken.java
    │               │   │   ├── 🗂 repository
    │               │   │   │   ├── 📑 ManagerRepository.java
    │               │   │   │   └── 📑 RefreshTokenRepository.java
    │               │   │   └── 🗂 service
    │               │   │       ├── 📑 AuthService.java
    │               │   │       ├── 📑 CustomUserDetailsService.java
    │               │   │       └── 📑 ManagerService.java
    │               │   │   ├── 🗂 util
    │               │   │       └── 📑 SecurityUtil.java
    │               │   ├── 🗂 notice
    │               │   │   ├── 🗂 controller
    │               │   │   │   ├── 📑 AdminNoticeController.java
    │               │   │   │   └── 📑 NoticeController.java
    │               │   │   ├── 🗂 dto
    │               │   │   │   └── 📑 NoticeDto.java
    │               │   │   ├── 🗂 entity
    │               │   │   │   └── 📑 Notice.java
    │               │   │   ├── 🗂 repository
    │               │   │   │   └── 📑 NoticeRepository.java
    │               │   │   └── 🗂 service
    │               │   │       └── 📑 NoticeService.java
    │               │   ├── 🗂 reservation
    │               │   │   ├── 🗂 controller
    │               │   │   │   └── 📑 ReservationController.java
    │               │   │   ├── 🗂 dto
    │               │   │   │   ├── 📑 ReservationRequestDto.java
    │               │   │   │   └── 📑 ReservationSaveDto.java
    │               │   │   ├── 🗂 entity
    │               │   │   │   └── 📑 ReservationEntity.java
    │               │   │   ├── 🗂 repository
    │               │   │   │   └── 📑 ReservationRepository.java
    │               │   │   └── 🗂 service
    │               │   │       ├── 📑 ReservationService.java
    │               │   │       └── 📑 ReservationServiceImpl.java
    │               │   └── 🗂 global
    │               │       ├── 🗂 config
    │               │       │   ├── 📑 SecurityConfig.java
    │               │       │   ├── 📑 SwaggerConfig.java
    │               │       │   └── 📑 WebConfig.java
    │               │       ├── 🗂 jwt
    │               │       │   ├── 📑 JwtAccessDeniedHandler.java
    │               │       │   ├── 📑 JwtAuthenticationEntryPoint.java
    │               │       │   ├── 📑 JwtAuthenticationFilter.java
    │               │       │   └── 📑 JwtTokenProvider.java
    │               │       ├── 🗂 S3
    │               │       │   └── 📑 S3Config.java
    │               │       └── 🗂 test
    │               │           ├── 📑 MakeImage.java
    │               │           └── 📑 WebTest.java
    └── 🗂 resources
        ├── 📑 application.yml
        ├── 📑 application-secret.yml
        └── 📑 Make_Resources_.txt
```
##

## 📌 기능소개

<p align="center" style="font-size: 24px;">
    <strong>1. 관리자의 분실물 게시판을 통해 축제기간 발생한 분실물에 대한 정보를 제공</strong>
</p>
<p align="center">
    <img src="https://github.com/user-attachments/assets/938ef18a-9857-42e6-b4ec-0c00d795c173" width="45%" />
</p>

<p align="center" style="font-size: 24px;">
    <strong>2. 커뮤니티 기능을 통해 한림대 학생들만의 유대감 형성</strong>
</p>
<p align="center">
    <img src="https://github.com/user-attachments/assets/f487804f-e813-4861-8c27-b27148534fac" width="45%" />
</p>

<p align="center" style="font-size: 24px;">
    <strong>3. 주점예약 및 확인 기능을 통해 기존 방식에서 안전하게 주점을 안내</strong>
</p>
<p align="center">
    <img src="https://github.com/user-attachments/assets/b05fd180-3c0b-4d16-910d-49d258c91628" width="30%" style="margin: 0 1%;" /> <br>
    <img src="https://github.com/user-attachments/assets/8f7782b4-cc28-4921-9ff3-58231c0f186c" width="30%" style="margin: 0 1%;" />
    <img src="https://github.com/user-attachments/assets/601f7aa6-ee62-457a-bb7d-507681aae3a4" width="30%" style="margin: 0 1%;" />
</p>
