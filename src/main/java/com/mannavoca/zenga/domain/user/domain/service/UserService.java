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

    /**
     * Social ID로 User 조회
     * @param socialId Social ID
     * @return User 객체
     */
    @Transactional
    public User createUserBySocialId(String socialId) {
        return userRepository.save(User.builder()
                .socialId(socialId)
                .build());
    }

    /**
     * User 온보딩
     * @param userId User ID
     * @param userOnboardingRequestDto User 온보딩 요청 DTO
     * @return 온보딩된 User 객체
     */
    @Transactional
    public User onboardUser(Long userId, UserOnboardingRequestDto userOnboardingRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO: 에러 처리 로직 구현 필요
        user.onboardUser(userOnboardingRequestDto);
        return userRepository.save(user);
    }

    /**
     * User 정보 조회
     * @param userId User ID
     * @return User 객체
     */
    public User getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO: 에러 처리 로직 구현 필요
        return user;
    }
}
