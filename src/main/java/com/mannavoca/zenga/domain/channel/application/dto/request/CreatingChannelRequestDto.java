package com.mannavoca.zenga.domain.channel.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreatingChannelRequestDto {
    @NotBlank(message = "채널 이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "채널 로고 이미지 URL을 입력해주세요.")
    private String logoImageUrl;
//    @NotBlank(message = "채널 설명을 입력해주세요.")
//    private String description;
}
