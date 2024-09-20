package com.omniscient.omniscientback.login.service;

import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserRole;
import com.omniscient.omniscientback.login.repository.UserRepository;
import com.omniscient.omniscientback.login.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

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

    public long getUserCount() {
        return userRepository.count();
    }

    public UserEntity updateUser(Integer userId, UserDTO userDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(UserRole.valueOf(userDto.getRole())); // 역할 업데이트
        return userRepository.save(user);
    }

    public void setUserRole(Integer userId, String role) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(UserRole.valueOf(role)); // 역할 업데이트
        userRepository.save(user);
    }

    public String getUserRole(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRole().name(); // 사용자 역할 반환
    }
}
