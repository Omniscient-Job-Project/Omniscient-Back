package com.omniscient.omniscientback.login.service;
import com.omniscient.omniscientback.login.exception.JwtTokenException;
import com.omniscient.omniscientback.login.model.JwtTokenDTO;

import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private final String secretKey;
    private Key key;


    @Autowired
    public JwtTokenProvider(@Value("${JWT_SECRET_KEY}") String secretKey, UserRepository userRepository) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    // Access Token 생성
    public String createAccessToken(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));
        // userEntity의 권한을 가져옴
        Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities(); // 권한을 가져오는 메서드 필요
        return generateToken(userEntity.getUserId(), authorities).getAccessToken();
    }

    // Refresh Token 생성
    public String createRefreshJwt(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));
        // userEntity의 권한을 가져옴
        Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities(); // 권한을 가져오는 메서드 필요
        return generateToken(userEntity.getUserId(), authorities).getRefreshToken();
    }

    // Access Token 만료 시간 반환 메서드
    public long getAccessTokenExpiry() {
        return ExpireTime.ACCESS_TOKEN_EXPIRE_TIME;
    }

    // Refresh Token 만료 시간 반환 메서드
    public long getRefreshTokenExpiry() {
        return ExpireTime.REFRESH_TOKEN_EXPIRE_TIME;
    }




    // Authentication을 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtTokenDTO generateToken(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    // name, authorities를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtTokenDTO generateToken(String name, Collection<? extends GrantedAuthority> authorities) {
        String authString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        // AccessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authString)
                .claim("type", TYPE_ACCESS)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // RefreshToken 생성
        String refreshToken = Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ExpireTime.REFRESH_TOKEN_EXPIRE_TIME)) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


        // 사용자 정보 업데이트 및 RefreshToken 저장

        UserEntity userEntity = userRepository.findByUserId(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        userEntity.setRefreshToken(refreshToken); // refreshToken 업데이트
        userRepository.save(userEntity); // 사용자 정보 저장

        return new JwtTokenDTO(accessToken, refreshToken,
                ExpireTime.ACCESS_TOKEN_EXPIRE_TIME,
                ExpireTime.REFRESH_TOKEN_EXPIRE_TIME,
                null); // 또는 적절한 에러 메시지를 여기에 넣어줍니다.
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.err.println("Invalid JWT Token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Expired JWT Token: " + e.getMessage());
            throw new JwtTokenException("JWT 토큰 만료");
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT Token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    // 액세스 토큰으로부터 정보 추출
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 헤더에서 액세스 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}