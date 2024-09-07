package com.omniscient.omniscientback.manager.user.controller;

import com.omniscient.omniscientback.manager.user.model.User;
import com.omniscient.omniscientback.manager.user.model.UserDTO;
import com.omniscient.omniscientback.manager.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:8083")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers().stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable("id") Integer id, @RequestBody UserDTO updatedUser) {
        User user = userService.updateUserRole(id, updatedUser.getRole());
        UserDTO userDTO = new UserDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/user-count")
    public ResponseEntity<Long> getUserCount() {
        long userCount = userService.countUsers();
        return ResponseEntity.ok(userCount);
    }
}
