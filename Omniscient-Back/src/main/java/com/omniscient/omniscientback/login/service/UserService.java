package com.omniscient.omniscientback.login.service;

import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserDTO;
import com.omniscient.omniscientback.login.model.UserRole;
import com.omniscient.omniscientback.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 모든 사용자 조회 (관리자 전용)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // 특정 사용자 조회 (userId로 검색)
    public UserEntity getUserByUserId(String userId) { // userId를 String으로 변경
        return userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
    }

    // 현재 로그인한 사용자 조회
    public UserEntity getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUserId(userId); // 재사용
    }

    // 회원수 조회 (관리자 전용)
    public long getUserCount() {
        return userRepository.count();
    }

    // 사용자 생성
    public UserEntity createUser(UserDTO userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        UserEntity user = new UserEntity.Builder()
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .password(encodedPassword)
                .userStatus(userDto.isUserStatus())
                .role(UserRole.valueOf(userDto.getRole()))
                .birthDate(userDto.getBirthDate())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .build();
        return userRepository.save(user);
    }

    // 사용자 업데이트 (userId를 String으로 변경)
    public UserEntity updateUser(String userId, UserDTO userDto) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
        user.setRole(UserRole.valueOf(userDto.getRole())); // 역할 업데이트
        return userRepository.save(user);
    }

    // 사용자 역할 설정 (userId를 String으로 처리)
    public void setUserRole(String userId, String role) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
        user.setRole(UserRole.valueOf(role)); // 역할 업데이트
        userRepository.save(user);
    }

    // 사용자 역할 조회
    public String getUserRole(String userId) { // userId를 String으로 변경
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
        return user.getRole().name(); // 사용자 역할 반환
    }

    @Transactional
    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
        userRepository.delete(user); // DB에서 사용자 삭제
    }
}
