package com.mannavoca.zenga.domain.album.application.dto.response;

import lombok.Getter;

@Getter
public class GetAlbumDetailResponseDto {

    private Long id;

    private String imageUrl;

    private String title;

    private String text;

    public static GetAlbumDetailResponseDto of(Long id, String imageUrl, String title, String text) {
        GetAlbumDetailResponseDto getAlbumDetailResponseDto = new GetAlbumDetailResponseDto();
        getAlbumDetailResponseDto.id = id;
        getAlbumDetailResponseDto.imageUrl = imageUrl;
        getAlbumDetailResponseDto.title = title;
        getAlbumDetailResponseDto.text = text;

        return getAlbumDetailResponseDto;
    }
}
