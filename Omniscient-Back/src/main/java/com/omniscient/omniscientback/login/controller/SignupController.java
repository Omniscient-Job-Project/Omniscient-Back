package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {

    private final SignupService signupService;
    private static final Logger logger = LoggerFactory.getLogger(SignupService.class);

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    //회원가입(사용자)
    @PostMapping("/post")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
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


    @PostMapping("/admin/signup")
    public ResponseEntity<String> adminSignup(@RequestBody SignupDTO signupDTO) {
        logger.info("Received admin signup request for userId: {}", signupDTO.getUserId());

        if (!signupService.isAdminSignupFieldsValid(signupDTO)) {
            logger.warn("Admin signup failed: Missing required fields");
            return ResponseEntity.badRequest().body("아이디, 비밀번호, 이름은 필수 입력 항목입니다.");
        }

        boolean isSignupSuccessful = signupService.adminSignup(signupDTO);

        if (isSignupSuccessful) {
            logger.info("Admin signup successful for userId: {}", signupDTO.getUserId());
            return ResponseEntity.ok("관리자 회원가입 성공!");
        } else {
            logger.warn("Admin signup failed for userId: {}", signupDTO.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디가 이미 존재합니다.");
        }
    }
}
