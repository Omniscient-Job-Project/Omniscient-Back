package com.omniscient.omniscientback.login.repository;

import com.omniscient.omniscientback.login.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUserId(String userId);
    Optional<UserEntity> findByUserId(String userId);
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    void deleteByUserId(String userId);
}
