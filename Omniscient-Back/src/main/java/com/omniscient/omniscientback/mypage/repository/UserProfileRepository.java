package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    List<UserProfile> findAllByActiveTrue();
    UserProfile findByEmailAndActiveTrue(String email);
}