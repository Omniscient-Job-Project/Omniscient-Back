package com.omniscient.omniscientback.login.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;


@Service
public class JwtService {

    private final String jwtAccessSecretKey;
    private final String jwtRefreshSecretKey;


    public JwtService(
            @Value("${jwt.access.secret}") String jwtAccessSecretKey,
            @Value("${jwt.refresh.secret}") String jwtRefreshSecretKey) {
        this.jwtAccessSecretKey = jwtAccessSecretKey;
        this.jwtRefreshSecretKey = jwtRefreshSecretKey; // 초기화
    }

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofHours(2).toMillis());
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtAccessSecretKey)
                .compact();
    }

    public String createRefreshJwt(String userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(30).toMillis());
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtRefreshSecretKey)
                .compact();
    }

    // JWT 검증 메서드
    public Claims validateToken(String token, boolean isRefreshToken) {
        // 리프레시 토큰 여부에 따라 비밀 키 선택
        String secretKey = isRefreshToken ? jwtRefreshSecretKey : jwtAccessSecretKey;

        try {
            // 토큰을 파싱하여 클레임을 추출
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey) // 서명 검증을 위한 비밀 키 설정
                    .parseClaimsJws(token) // 토큰 파싱
                    .getBody(); // 클레임 반환
            return claims; // 검증된 클레임 반환
        } catch (SignatureException e) {
            // 서명이 유효하지 않은 경우
            throw new RuntimeException("Invalid JWT signature"); // 예외 처리
        } catch (Exception e) {
            // 만료된 토큰 등 기타 예외 처리
            throw new RuntimeException("Invalid JWT token"); // 예외 처리
        }
    }
}
