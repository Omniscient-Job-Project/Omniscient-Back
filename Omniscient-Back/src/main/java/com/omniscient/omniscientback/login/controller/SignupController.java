package com.omniscient.omniscientback.login.controller;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        System.out.println("Received admin signup request for userId: " + signupDTO.getUserId());

        try {
            // 관리자 회원가입 전용 필드 검증 메소드 사용
            if (!signupService.adminSignup(signupDTO)) {
                System.out.println("Invalid admin signup request: " + signupDTO);
                return ResponseEntity.badRequest().body("필수 필드(아이디, 비밀번호, 이름)를 올바르게 입력해주세요.");
            }

            boolean isSignupSuccessful = signupService.adminSignup(signupDTO);

            if (isSignupSuccessful) {
                System.out.println("Admin signup successful for userId: " + signupDTO.getUserId());
                return ResponseEntity.ok("관리자 회원가입 성공!");
            } else {
            System.out.println("Admin signup failed for userId: " + signupDTO.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("관리자 회원가입 실패. 아이디가 이미 존재하거나 다른 문제가 발생했습니다.");
        }
    } catch (Exception e) {
        System.err.println("Error during admin signup for userId: " + signupDTO.getUserId());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
}
