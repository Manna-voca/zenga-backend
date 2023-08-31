package com.mannavoca.zenga.domain.album.application.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAlbumListResponseDto {

    private List<AlbumResponseDto> albumList;

    public static GetAlbumListResponseDto of(List<AlbumResponseDto> albumList) {
        GetAlbumListResponseDto getAlbumListResponseDto = new GetAlbumListResponseDto();
        getAlbumListResponseDto.albumList = albumList;

        return getAlbumListResponseDto;
    }


    public static class AlbumResponseDto {
        private Long id;

        private String imageUrl;

        public static AlbumResponseDto of(Long id, String imageUrl) {
            AlbumResponseDto getAlbumListResponseDto = new AlbumResponseDto();
            getAlbumListResponseDto.id = id;
            getAlbumListResponseDto.imageUrl = imageUrl;

            return getAlbumListResponseDto;
        }
    }
}
