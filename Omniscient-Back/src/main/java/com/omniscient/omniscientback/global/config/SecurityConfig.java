package com.omniscient.omniscientback.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // RoleHierarchy 설정 추가
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http)) // CORS 활성화
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(authz -> authz
                        // Swagger 경로에 대한 요청 허용
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/login/post").permitAll()
                        .requestMatchers("/api/v1/signup/post").permitAll()
                        .requestMatchers("/api/v1/signout/post").permitAll()
                        .requestMatchers("/api/v1/login/admin/login").permitAll()
                        .requestMatchers("/api/v1/signup/admin/signup").permitAll()
                        .requestMatchers("/api/v1/notice/**").permitAll()
                        .requestMatchers("/api/v1/testjob").permitAll()  // 인증 없이 접근 허용
                        // 경로 변수 {userId}를 '*'로 수정하여 경로 매칭 정확도 향상
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/{userId}").hasRole("ADMIN") // 관리자만 허용
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/me").authenticated() // 인증된 사용자만 허용
                        .requestMatchers("/api/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
