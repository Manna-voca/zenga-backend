package com.mannavoca.zenga.domain.member.application.dto.request;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreatingMemberRequestDto {
    @NotNull(message = "채널 ID를 입력해주세요.")
    private Long channelId;
    @NotBlank(message = "프로필 사진 주소를 입력해주세요.")
    private String profileImageUrl;
    @NotBlank(message = "이름을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "한줄소개를 입력해주세요.")
    private String introduction;
    @NotBlank(message = "권한을 입력해주세요.")
    private LevelType level;
}
