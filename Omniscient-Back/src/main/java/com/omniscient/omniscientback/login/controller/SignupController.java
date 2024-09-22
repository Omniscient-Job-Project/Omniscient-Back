package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {

    private final SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    //회원가입 요청
    @PostMapping("/post")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO){
        System.out.println("Received signup request: " + signupDTO);

        //모든 필드 빈값 검증
        if (!signupService.isAllFieldsValid(signupDTO)) {
            return ResponseEntity.badRequest().body("모든 필드를 올바르게 입력해주세요.");
        }

        boolean isSignupSuccessful = signupService.signup(signupDTO);

        if (isSignupSuccessful) {
            return ResponseEntity.ok("회원가입 성공!!!!!!");
        } else {
            return ResponseEntity.badRequest().body("아이디가 이미 존재합니다.");
        }
    }
}
