package com.omniscient.omniscientback.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.CacheControl.maxAge;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8083")  // Vue.js가 실행되는 도메인 추가
                .allowedOrigins(allowedOrigins)  // Vue.js가 실행되는 도메인 추가
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Custom-Header")
                .allowCredentials(true)  // 쿠키나 인증 정보를 포함할 경우 true로 설정
                .maxAge(MAX_AGE_SECS);  // Preflight 요청 캐시 지속 시간 설정
    }
}