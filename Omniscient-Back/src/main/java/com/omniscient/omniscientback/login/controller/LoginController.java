package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final SignupService signupService;

    public LoginController(SignupService signupService) {
        this.signupService = signupService;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignupDTO signupDTO) {
        //SignupDTO 에서 사용자명과 패스워드 가져와 인증 시도
        boolean isAuthenticated = signupService.authenticate(signupDTO.getUsername(), signupDTO.getPassword());

        //인증에 성공한다면
        if (isAuthenticated) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 사용자명 또는 비밀번호");
        }

    }
}
