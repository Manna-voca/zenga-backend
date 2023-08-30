package com.mannavoca.zenga.domain.channel.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChannelResponseDto {
    private Long id;
    private String name;
    //    private Integer capacity;
    private String logoImageUrl;
//    private String description;
    private String code;
//    private LocalDateTime finishedAt;
}
