package com.mannavoca.zenga.domain.user.domain.repository;

import com.mannavoca.zenga.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserBySocialId(String socialId);
}
