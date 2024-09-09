package com.omniscient.omniscientback.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()  // CORS 활성화
                .and()
                .csrf().disable()  // 필요한 경우 CSRF 비활성화
                .authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll()  // "/api" 경로 접근 허용
                .anyRequest().authenticated();  // 그 외 모든 요청 인증 필요

        return http.build();
    }

    //회원가입 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

