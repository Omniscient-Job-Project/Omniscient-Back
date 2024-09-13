package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.JwtTokenDTO;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.JwtTokenProvider;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin(origins = "http://localhost:8083")
public class LoginController {

    private final SignupService signupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(SignupService signupService, JwtTokenProvider jwtTokenProvider) {
        this.signupService = signupService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //로그인
    @PostMapping("/post")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody SignupDTO signupDTO) {
        boolean isAuthenticated = signupService.authenticate(signupDTO.getUserId(), signupDTO.getPassword());

        JwtTokenDTO tokenDTO = new JwtTokenDTO();

        if (isAuthenticated) {
            // 사용자 ID를 통해 JWT 토큰 생성
            String accessToken = jwtTokenProvider.createAccessToken(signupDTO.getUserId());
            String refreshToken = jwtTokenProvider.createRefreshJwt(signupDTO.getUserId());

            // 토큰과 만료 시간을 포함한 DTO 생성
            tokenDTO = new JwtTokenDTO(
                    accessToken,
                    refreshToken,
                    jwtTokenProvider.getAccessTokenExpiry(),
                    jwtTokenProvider.getRefreshTokenExpiry(),
                    null
            );
        } else {
            tokenDTO.setErrorMessage("로그인 실패: 잘못된 아이디 또는 비밀번호입니다");
            return ResponseEntity.status(401).body(tokenDTO); // 오류 메시지를 포함한 DTO 반환
        }

        return ResponseEntity.ok(tokenDTO); // JWT 토큰을 클라이언트에 반환
    }
    }

