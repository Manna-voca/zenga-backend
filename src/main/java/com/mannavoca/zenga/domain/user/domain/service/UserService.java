package com.mannavoca.zenga.domain.user.domain.service;

import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
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

    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO: 에러 처리 로직 구현 필요
        return UserInfoResponseDto.of(user);
    }
}
