package com.mannavoca.zenga.domain.album.application.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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


    @Getter
    public static class AlbumResponseDto {
        private Long id;

        private String imageUrl;

        private String title;

        private String content;

        private LocalDateTime albumCreatedDate;

        public static AlbumResponseDto of(Long id, String imageUrl, LocalDateTime albumCreatedDate, String title, String content) {
            AlbumResponseDto getAlbumListResponseDto = new AlbumResponseDto();
            getAlbumListResponseDto.id = id;
            getAlbumListResponseDto.imageUrl = imageUrl;
            getAlbumListResponseDto.albumCreatedDate = albumCreatedDate;
            getAlbumListResponseDto.title = title;
            getAlbumListResponseDto.content = content;

            return getAlbumListResponseDto;
        }
    }
}
