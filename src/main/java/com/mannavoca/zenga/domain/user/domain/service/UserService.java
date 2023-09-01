package com.mannavoca.zenga.domain.user.domain.service;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.domain.user.application.dto.request.UpdatingUserInfoRequestDto;
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
     * @param user User 객체
     * @param updatingUserInfoRequestDto User 온보딩 요청 DTO
     * @return 온보딩된 User 객체
     * @throws BusinessException Error.USER_ALREADY_ONBOARDED 유저가 이미 온보딩한 경우
     */
    @Transactional
    public User updateUser(User user, final UpdatingUserInfoRequestDto updatingUserInfoRequestDto) {

        user.updateUser(updatingUserInfoRequestDto);
        return userRepository.save(user);
    }
}
