package com.mannavoca.zenga.domain.album.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAlbumListRequestDto {

    @NotNull
    private Long memberId;

}
