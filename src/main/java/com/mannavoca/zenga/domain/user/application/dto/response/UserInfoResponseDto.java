package com.mannavoca.zenga.domain.user.application.dto.response;

import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import com.mannavoca.zenga.domain.user.domain.entity.enumType.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {
    private Long id;
    private RoleType role;
    private String name;
    private GenderType gender;
    private LocalDate birth;
    private String socialId;

    @Builder
    public UserInfoResponseDto(Long id, RoleType role, String name, GenderType gender, LocalDate birth, String socialId) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.socialId = socialId;
    }

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto
                .builder()
                .id(user.getId())
                .role(user.getRole())
                .name(user.getName())
                .gender(user.getGender())
                .birth(user.getBirth())
                .socialId(user.getSocialId())
                .build();
    }
}
