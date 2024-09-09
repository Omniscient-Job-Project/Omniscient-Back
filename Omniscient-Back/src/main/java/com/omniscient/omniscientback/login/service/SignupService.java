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

    //회원가입 아이디 중복 확인
    public boolean signup(SignupDTO signupDTO) {
        String username = signupDTO.getUsername();
        String password = signupDTO.getPassword();


        // 아이디 중복 체크
        if (userRepository.existsByUsername(username)) {
            return false;  // 중복된 아이디가 존재하면 false 반환
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(UserRole.ROLE_USER); // 기본 역할 설정
        userEntity.setUserStatus(true);

        userRepository.save(userEntity);
        return true;   //회원가입 성공 시 true 반환
    }

    //회원탈퇴



    //로그인
    public boolean authenticate(String username, String password){

        UserEntity userEntity = userRepository.findByUsername(username);

        //사용자 정보 존재여부 확인
        if(userEntity != null){
        // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
            return passwordEncoder.matches(password, userEntity.getPassword());
        }
        // 사용자가 존재하지 않으면 인증 실패
            return false;
    }


}
