    package com.hallymfestival.HallymFestival2024BackEnd.global.config;

    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


    // 스프링 서버 전역적으로 CORS 설정
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOrigins("http://hallym-festival.com","https://hallym-festival.com","http://github-action-s3-hallym-festival-develop.s3-website.ap-northeast-2.amazonaws.com","http://github-action-s3-list-app.s3-website.ap-northeast-2.amazonaws.com","http://localhost:8080", "http://localhost:5175","http://localhost:5173","http://localhost:8081", "http://localhost:3000","http://3.39.62.170","http://13.209.218.51") // 허용할 출처 추가
                    .allowedMethods("GET", "POST","DELETE","PUT") // 허용할 HTTP method
                    .allowCredentials(true) // 쿠키 인증 요청 허용
                    //.allowedOriginPatterns("*") //추가한 부분
                    .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱2
        }
//

    }
