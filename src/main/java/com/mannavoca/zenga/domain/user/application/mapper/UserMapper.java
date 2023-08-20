package com.mannavoca.zenga.domain.user.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
import com.mannavoca.zenga.domain.user.domain.entity.User;

@Mapper
public class UserMapper {
    public static UserInfoResponseDto mapUserToUserInfoResponseDto(User user) {
        return UserInfoResponseDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .birth(user.getBirth())
                .build();
    }
}
