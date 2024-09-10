package com.omniscient.omniscientback.login.repository;

import com.omniscient.omniscientback.login.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);
    Boolean existsByUserId(String userId);
    UserEntity findByUsername(String username);
}
