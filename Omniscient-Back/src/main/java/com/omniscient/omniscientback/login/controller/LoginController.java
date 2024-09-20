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

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        // Authorization 헤더에서 Bearer 토큰 추출
        String token = authorization.substring(7); // "Bearer " 이후의 토큰 부분만 가져옴

        // 토큰 무효화 로직 (필요에 따라 추가)
        boolean isInvalidated = signupService.invalidateRefreshToken(token);

        if (isInvalidated) {
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.status(400).body("로그아웃 실패");
        }
    }







//    @PostMapping("/deactivate")
//    public ResponseEntity<String> deactivateAccount(@RequestHeader("Authorization") String authorization) {
//        try {
//            // Authorization 헤더에서 Bearer 토큰 추출
//            String token = authorization.substring(7); // "Bearer " 이후의 토큰 부분만 가져옴
//
//            // 토큰에서 사용자 ID 추출
//            String userId = jwtTokenProvider.getUserIdFromToken(token);
//
//            if (userId != null) {
//                boolean isDeactivated = signupService.deactivateAccount(userId);
//                if (isDeactivated) {
//                    return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
//                } else {
//                    return ResponseEntity.status(400).body("회원 탈퇴에 실패했습니다.");
//                }
//            } else {
//                return ResponseEntity.status(401).body("유효하지 않은 토큰입니다.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
//        }
//    }

}

