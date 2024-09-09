package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}