package com.omniscient.omniscientback.login.controller;

import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserDTO;
import com.omniscient.omniscientback.login.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 관리자: 모든 회원 목록 조회
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 일반 사용자: 자신의 회원 정보 조회
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserEntity> getCurrentUser() {
        UserEntity currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    // 특정 사용자 정보 조회 (관리자 또는 해당 사용자만 가능)
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.name")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) { // userId를 String으로 변경
        UserEntity user = userService.getUserByUserId(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // 회원수 조회 (관리자 전용)
    @GetMapping("/user-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }

    // 사용자 정보 수정 (관리자 또는 본인만 가능)
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.name")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDto) {
        UserEntity updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // 사용자 역할 변경 (관리자만 가능)
    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> setUserRole(@PathVariable String userId, @RequestBody Map<String, String> body) {
        String role = body.get("role");
        userService.setUserRole(userId, role); // 역할 변경
        return ResponseEntity.ok().build();
    }


    // 사용자 삭제 (관리자만 가능, PUT 사용)
    @PutMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
