package com.mannavoca.zenga.domain.user.domain.service;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
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

    /**
     * Social ID로 User 조회, 없을 경우 새로 생성하여 return
     * @param socialId Social ID
     * @return User 객체
     */
    public User findOrCreateBySocialId(String socialId) {
        if (userRepository.findUserBySocialId(socialId).isPresent()) {
            return userRepository.findUserBySocialId(socialId).get();
        } else {
            return userRepository.save(User.builder()
                    .socialId(socialId)
                    .build());
        }
    }

    /**
     * Social ID로 User 조회, 없을 경우 null 리턴
     * @param socialId Social ID
     * @return User 객체 or null
     */
    public User findBySocialId(String socialId) {
        return userRepository.findUserBySocialId(socialId).orElse(null);
    }

    /**
     * User ID로 User 조회
     * @param id User ID
     * @return User 객체
     * @throws BusinessException Error.USER_NOT_FOUND
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> BusinessException.of(Error.USER_NOT_FOUND));
    }

    /**
     * User ID의 유효성 검증
     * @param id User ID
     * @throws BusinessException Error.USER_NOT_FOUND
     */
    public void validateUserId(Long id) {
        userRepository.findById(id).orElseThrow(() -> BusinessException.of(Error.USER_NOT_FOUND));
    }
}
