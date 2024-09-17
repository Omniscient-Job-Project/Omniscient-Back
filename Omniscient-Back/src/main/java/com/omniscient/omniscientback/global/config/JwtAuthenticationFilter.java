package com.omniscient.omniscientback.global.config;

import com.omniscient.omniscientback.login.service.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider로 변경

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider; // JwtTokenProvider 주입
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제거
            try {
                // JWT 토큰 검증
                if (jwtTokenProvider.validateToken(token)) { // JwtTokenProvider 사용
                    Claims claims = jwtTokenProvider.parseClaims(token); // Claims 추출
                    String userId = claims.getSubject(); // 사용자 ID 추출

                    // 사용자 권한 설정 (예시)
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보 설정
                }
            } catch (Exception e) {
                System.err.println("JWT 검증 실패: " + e.getMessage());
                SecurityContextHolder.clearContext(); // 유효하지 않은 토큰 처리
            }
        }

        // 다음 필터로 요청 및 응답 전달
        filterChain.doFilter(request, response);
    }
}
