package com.omniscient.omniscientback.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info; // 필요한 클래스를 import
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Swagger 설정을 위한 OpenAPI 빈 정의
    @Bean
    public OpenAPI customOpenAPI() {
        // JWT 보안 스키마 이름
        String jwt = "JWT";

        // JWT 보안을 적용하는 SecurityRequirement 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // JWT 보안 스키마 구성: HTTP 타입, Bearer 포맷
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)  // HTTP 타입 스키마
                .scheme("bearer")  // Bearer 토큰 사용
                .bearerFormat("JWT")  // JWT 포맷
        );

        // OpenAPI 인스턴스를 생성하고, 구성 요소와 보안 설정 추가
        return new OpenAPI()
                .components(components)  // 보안 스키마 추가
                .info(apiInfo())  // API 기본 정보 설정
                .addSecurityItem(securityRequirement);  // 보안 요구사항 설정
    }

    // API 정보 설정: 제목, 설명, 버전 등을 정의
    private Info apiInfo() {
        return new Info()
                .title("Omniscient API Test")  // API의 제목
                .description("Omniscient API 명세서 by Swagger")  // API 설명
                .version("1.0.0");  // API 버전
    }
}
