package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.model.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
    UserInfo findByNameOrEmail(String username, String email);
    Optional<UserInfo> findByEmail(String email);
}
