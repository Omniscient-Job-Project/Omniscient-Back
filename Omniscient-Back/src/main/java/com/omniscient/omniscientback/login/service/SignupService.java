package com.omniscient.omniscientback.login.service;


import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserRole;
import com.omniscient.omniscientback.login.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;


    }

    // 모든 필드의 유효성 검증
    public boolean isAllFieldsValid(SignupDTO signupDTO) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return signupDTO.getUserId() != null && !signupDTO.getUserId().trim().isEmpty() &&
                signupDTO.getUsername() != null && !signupDTO.getUsername().trim().isEmpty() &&
                signupDTO.getPassword() != null && !signupDTO.getPassword().trim().isEmpty() &&
                signupDTO.getEmail() != null && signupDTO.getEmail().matches(emailRegex) &&
                signupDTO.getPhoneNumber() != null && !signupDTO.getPhoneNumber().trim().isEmpty();

    }


    // 회원가입
    public boolean signup(SignupDTO signupDTO) {
        // 모든 필드 빈 값 검증
        if (!isAllFieldsValid(signupDTO)) {
            return false; // 모든 필드가 유효하지 않으면 false 반환
        }

        String userId = signupDTO.getUserId();
        String username = signupDTO.getUsername();
        String password = signupDTO.getPassword();
        String email = signupDTO.getEmail();
        String phoneNumber = signupDTO.getPhoneNumber();

        // 아이디 중복 체크
        if (userRepository.existsByUserId(userId)) {
            return false; // 중복된 아이디가 존재하면 false 반환
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // UserEntity 생성
        UserEntity userEntity = new UserEntity.Builder()
                .userId(userId)
                .username(username)
                .password(encodedPassword)
                .role(UserRole.USER) // 기본 역할 설정
                .userStatus(true) // 기본 사용자 상태 설정
                .phoneNumber(phoneNumber)
                .email(email)
                .refreshToken("") // 기본값 설정 (null 아님)
                .build();

        // UserEntity 저장
        userRepository.save(userEntity);
        return true; // 회원가입 성공 시 true 반환
    }

    // 로그인
    public boolean authenticate(String userId, String password) {
        // userRepository에서 userId로 사용자를 찾음
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElse(null); // Optional을 사용할 경우

        // 사용자 정보가 존재하는지 확인
        if (userEntity != null && userEntity.getPassword() != null) {
            // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
            return passwordEncoder.matches(password, userEntity.getPassword());
        }

        // 사용자가 존재하지 않거나 비밀번호가 없는 경우 인증 실패
        return false;
    }

    // Refresh 토큰 무효화 메서드
    public boolean invalidateRefreshToken(String token) {

        return true;
    }

//    public String generateVerificationCode() {
//        Random random = new Random();
//        int code = 100000 + random.nextInt(900000);
//        return String.format("%06d", code);
//    }


//    @Transactional
//    public boolean deactivateAccount(String userId) {
//        try {
//            // 1. 사용자 조회
//            UserEntity user = userRepository.findById(Integer.valueOf(userId))
//                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));
//
//            // 2. 사용자 계정 비활성화
//            user.setActive(false);
//            // 3. 계정 비활성화 시간 기록
//            user.setDeactivatedAt(LocalDateTime.now());
//            // 4. 개인정보 보호를 위한 데이터 익명화
//            user.setEmail("deactivated_" + userId + "@example.com");
//            user.setPhoneNumber(null);
//            // 5. 변경된 사용자 정보 저장
//            userRepository.save(user);
//            // 6. 비활성화 성공 반환
//            return true;
//        } catch (Exception e) {
//            // 예외 처리: 로그 기록 및 적절한 예외 전환
//            // 예: Logger.error("계정 비활성화 중 오류 발생: ", e);
//            throw new RuntimeException("계정 비활성화 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }

}






