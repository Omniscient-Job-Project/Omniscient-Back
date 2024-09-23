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

    @Value("${app.cors.allowedSwaggers}")
    private String[] allowedSwaggers;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(concatArrays(allowedOrigins, allowedSwaggers)) // 두 배열을 합쳐서 사용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Custom-Header")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

    // 두 배열을 합치는 헬퍼 메서드
    private String[] concatArrays(String[] array1, String[] array2) {
        String[] result = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
