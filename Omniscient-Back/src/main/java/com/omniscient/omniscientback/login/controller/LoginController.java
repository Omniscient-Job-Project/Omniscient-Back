package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.exception.JwtTokenException;
import com.omniscient.omniscientback.login.model.JwtTokenDTO;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.service.JwtTokenProvider;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


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


    //로그인(일반 사용자)
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
                    "USER",
                    null
            );
        } else {
            tokenDTO.setErrorMessage("로그인 실패: 잘못된 아이디 또는 비밀번호입니다");
            return ResponseEntity.status(401).body(tokenDTO); // 실패 시 401 상태 코드와 오류 메시지 반환
        }
        return ResponseEntity.ok(tokenDTO);  // 인증 성공 시, JWT 토큰과 함께 응답 반환
    }



    // 로그인(관리자)
    @PostMapping("/admin/login")
    public ResponseEntity<JwtTokenDTO> adminLogin(@RequestBody SignupDTO signupDTO) {
        System.out.println("관리자 로그인 요청 받음: " + signupDTO.getUserId());

        try {
            boolean isAuthenticated = signupService.authenticateAdmin(signupDTO.getUserId(), signupDTO.getPassword());

            if (isAuthenticated) {
                System.out.println("관리자 인증 성공: " + signupDTO.getUserId());
                String accessToken = jwtTokenProvider.createAccessToken(signupDTO.getUserId());
                String refreshToken = jwtTokenProvider.createRefreshJwt(signupDTO.getUserId());

                JwtTokenDTO tokenDTO = new JwtTokenDTO(
                        accessToken,
                        refreshToken,
                        jwtTokenProvider.getAccessTokenExpiry(),
                        jwtTokenProvider.getRefreshTokenExpiry(),
                        "ADMIN",
                        null
                );
                return ResponseEntity.ok(tokenDTO);
            } else {
                System.out.println("관리자 인증 실패: " + signupDTO.getUserId());
                JwtTokenDTO errorDTO = new JwtTokenDTO();
                errorDTO.setErrorMessage("관리자 로그인 실패: 잘못된 아이디 또는 비밀번호입니다");
                return ResponseEntity.status(401).body(errorDTO);
            }
        } catch (Exception e) {
            System.err.println("관리자 로그인 처리 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            JwtTokenDTO errorDTO = new JwtTokenDTO();
            errorDTO.setErrorMessage("서버 오류: 로그인 처리 중 문제가 발생했습니다");
            return ResponseEntity.status(500).body(errorDTO);
        }

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

    //이메일로 인증 코드를 전송하는 API
    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일 주소가 제공되지 않았습니다.");
        }

        try {
            boolean sent = signupService.sendVerificationCode(email);
            if (sent) {
                return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
            } else {
                return ResponseEntity.status(404).body("해당 이메일로 등록된 계정이 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("인증 코드 전송 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    //아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일 주소가 제공되지 않았습니다.");
        }

        String userId = signupService.findUserId(email);
        if (userId != null) {
            signupService.sendIdRecoveryEmail(email, userId);
            return ResponseEntity.ok("아이디가 이메일로 전송되었습니다.");
        } else {
            return ResponseEntity.status(404).body("해당 이메일로 등록된 계정이 없습니다.");
        }
    }

    //비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String newPassword = body.get("newPassword");
        String code = body.get("code");

        if (email == null || newPassword == null || code == null) {
            return ResponseEntity.badRequest().body("필요한 정보가 모두 제공되지 않았습니다.");
        }

        if (signupService.verifyCode(email, code)) {
            boolean isReset = signupService.resetPassword(email, newPassword);
            if (isReset) {
                return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
            } else {
                return ResponseEntity.status(500).body("비밀번호 재설정에 실패했습니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 인증 코드입니다.");
        }
    }


    //비밀번호 재설정 확인
    @PostMapping("/confirm-reset")
    public ResponseEntity<String> confirmReset(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");
        String newPassword = body.get("newPassword");

        if (email == null || code == null || newPassword == null) {
            return ResponseEntity.badRequest().body("필요한 정보가 모두 제공되지 않았습니다.");
        }

        if (signupService.verifyCode(email, code)) {
            if (signupService.resetPassword(email, newPassword)) {
                return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
            } else {
                return ResponseEntity.status(500).body("비밀번호 재설정에 실패했습니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 인증 코드입니다.");
        }
    }






    //회원 탈퇴
//    @PostMapping("/deactivate")
//    public ResponseEntity<String> deactivate(@RequestHeader("Authorization") String authorization) {
//        try {
//            // Authorization 헤더에서 Bearer 토큰 추출
//            if (authorization == null || !authorization.startsWith("Bearer ")) {
//                return ResponseEntity.status(401).body("유효하지 않은 토큰 형식입니다.");
//            }
//
//            String token = authorization.substring(7); // "Bearer " 이후의 토큰 부분만 가져옴

            // 토큰에서 사용자 ID 추출
//            String userId = jwtTokenProvider.getUserIdFromToken(token);
//
//            if (userId != null) {
//                boolean isDeactivated = signupService.deactivateAccount(userId);
//                if (isDeactivated) {
//                    return ResponseEntity.ok("계정이 성공적으로 비활성화되었습니다.");
//                } else {
//                    return ResponseEntity.status(400).body("계정 비활성화에 실패했습니다.");
//                }
//            } else {
//                return ResponseEntity.status(401).body("유효하지 않은 토큰입니다.");
//            }
//        } catch (UsernameNotFoundException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");




    // Refresh Token을 통해 Access Token 재발급 API
    @PostMapping("/renew-token")
    public ResponseEntity<String> renewAccessToken(@RequestBody String refreshToken) {
        try {
            // 새로운 Access Token 발급
            String newAccessToken = jwtTokenProvider.renewAccessToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } catch (JwtTokenException e) {
            // Refresh Token이 만료되었거나 유효하지 않을 경우 처리
            String errorMessage = e.getMessage() != null ? e.getMessage() : "유효하지 않은 리프레시 토큰입니다.";
            return ResponseEntity.status(401).body(errorMessage);
        }
    }
}







