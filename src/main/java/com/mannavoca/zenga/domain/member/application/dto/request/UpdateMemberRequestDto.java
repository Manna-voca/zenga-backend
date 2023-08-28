package com.mannavoca.zenga.domain.member.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateMemberRequestDto {

    @NotBlank(message = "프로필 사진 주소를 입력해주세요.")
    private String profileImageUrl;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "한줄소개를 입력해주세요.")
    private String description;
}
