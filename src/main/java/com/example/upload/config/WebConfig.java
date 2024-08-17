package com.example.upload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 서버 전역적으로 CORS 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://uploader-front.p-e.kr") // 허용할 출처 (로컬 리액트와 빌드된 리액트 파일이 담긴 Cloudfront 주소 <- 이때 도메인 주소 적어야함! 별칭 적으면 안됨!)
                .allowedMethods("GET", "POST", "DELETE", "HEAD") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}