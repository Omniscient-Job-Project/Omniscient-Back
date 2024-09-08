package com.omniscient.omniscientback.manager.user.service;

import com.omniscient.omniscientback.manager.user.model.Role;
import com.omniscient.omniscientback.manager.user.model.User;
import com.omniscient.omniscientback.manager.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public long countUsers() {
        return userRepository.count();
    }

    @Transactional
    public User updateUserRole(Integer userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
