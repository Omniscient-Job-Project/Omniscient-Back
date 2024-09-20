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

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.name")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer userId) {
        UserEntity user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDTO userDto) {
        UserEntity updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDTO userDto) {
        UserEntity user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/user-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> setUserRole(@PathVariable Integer userId, @RequestParam String role) {
        userService.setUserRole(userId, role);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getUserRole(@PathVariable Integer userId) {
        String role = userService.getUserRole(userId);
        return ResponseEntity.ok(role);
    }
}
