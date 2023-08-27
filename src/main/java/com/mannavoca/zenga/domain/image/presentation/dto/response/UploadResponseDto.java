package com.mannavoca.zenga.domain.image.presentation.dto.response;

import lombok.Getter;

@Getter
public class UploadResponseDto {
    private String url;

    private UploadResponseDto(String url) {
        this.url = url;
    }

    public static UploadResponseDto of(String url) {
        return new UploadResponseDto(url);
    }
}

