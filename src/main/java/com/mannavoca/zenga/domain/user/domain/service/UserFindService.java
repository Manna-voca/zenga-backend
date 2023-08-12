package com.mannavoca.zenga.domain.user.domain.service;

import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserFindService {
    private final UserRepository userRepository;

    public User findOrCreateBySocialId(String socialId) {
        if (userRepository.findUserBySocialId(socialId).isPresent()) {
            return userRepository.findUserBySocialId(socialId).get();
        } else {
            return userRepository.save(User.builder()
                    .socialId(socialId)
                    .build());
        }
    }

    public User findBySocialId(String socialId) {
        return userRepository.findUserBySocialId(socialId).orElseThrow();
    }
}
