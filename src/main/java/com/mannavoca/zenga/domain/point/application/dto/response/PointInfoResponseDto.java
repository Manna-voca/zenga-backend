package com.mannavoca.zenga.domain.point.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointInfoResponseDto {
    private Long pointId;
    private Integer point;
    private String description;
    private LocalDateTime createdAt;
}
