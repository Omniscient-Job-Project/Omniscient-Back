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

    // 특정 사용자 조회
    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // 현재 로그인한 사용자 조회 (userId로 조회)
    public UserEntity getCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
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

    // 사용자 업데이트
    public UserEntity updateUser(Integer userId, UserDTO userDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(UserRole.valueOf(userDto.getRole())); // 역할 업데이트
        return userRepository.save(user);
    }

    // 사용자 역할 설정
    public void setUserRole(Integer userId, String role) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(UserRole.valueOf(role)); // 역할 업데이트
        userRepository.save(user);
    }

    // 사용자 역할 조회
    public String getUserRole(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRole().name(); // 사용자 역할 반환
    }
    @Transactional
    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        // 사용자 상태를 비활성화하거나 실제로 데이터베이스에서 삭제
        userRepository.delete(user); // 실제로 DB에서 삭제하거나, 필요시 상태를 업데이트하는 로직으로 변경 가능
    }
}
