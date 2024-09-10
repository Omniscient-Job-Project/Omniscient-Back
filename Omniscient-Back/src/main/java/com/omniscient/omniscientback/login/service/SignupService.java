package com.omniscient.omniscientback.login.service;

import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserRole;
import com.omniscient.omniscientback.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return  signupDTO.getUserId() != null && !signupDTO.getUserId().trim().isEmpty() &&
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
                .username(signupDTO.getUsername())
                .password(encodedPassword)
                .role(UserRole.ROLE_USER)
                .userStatus(true)
                .phoneNumber(signupDTO.getPhoneNumber())
                .email(signupDTO.getEmail())
                .build();

        // UserEntity 저장
        userRepository.save(userEntity);
        return true; // 회원가입 성공 시 true 반환
    }

    // 로그인
    public boolean authenticate(String userId, String password) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        // 사용자 정보 존재여부 확인
        if (userEntity != null) {
            // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
            return passwordEncoder.matches(password, userEntity.getPassword());
        }
        // 사용자가 존재하지 않으면 인증 실패
        return false;
    }
}
