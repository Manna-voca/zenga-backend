package com.mannavoca.zenga.domain.member.application.dto.request;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreatingMemberRequestDto {
    @NotNull(message = "채널 ID는 필수입니다.")
    private Long channelId;
//    @NotBlank(message = "프로필 사진 링크는 필수입니다.")
    private String profileImageUrl;
    @NotBlank(message = "이름은 필수입니다.")
    private String nickname;
    @NotNull(message = "한줄소개는 필수입니다.")
    private String introduction;
    @NotNull(message = "권한을 입력해주세요.")
    @Enumerated(value = EnumType.STRING)
    private LevelType level;
}
