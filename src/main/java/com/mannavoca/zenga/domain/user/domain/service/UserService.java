package com.mannavoca.zenga.domain.user.domain.service;

import com.mannavoca.zenga.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUserBySocialId(String socialId) {
        return userRepository.save(User.builder()
                .socialId(socialId)
                .build());
    }

    @Transactional
    public User onboardUser(Long userId, UserOnboardingRequestDto userOnboardingRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO: 에러 처리 로직 구현 필요
        user.onboardUser(userOnboardingRequestDto);
        return userRepository.save(user);
    }

    public User getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO: 에러 처리 로직 구현 필요
        return user;
    }
}
