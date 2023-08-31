package com.mannavoca.zenga.domain.album.application;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.album.application.dto.request.GetAlbumDetailRequestDto;
import com.mannavoca.zenga.domain.album.application.dto.request.GetAlbumListRequestDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetAlbumDetailResponseDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetAlbumListResponseDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetParticipationWithResponseDto;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/album")
public class AlbumController {

    private final ParticipationService participationService;

    @GetMapping("/paticipation/{participationId}")
    public ResponseEntity<ResponseDto<GetAlbumDetailResponseDto>> getAlbumDetail(@Valid @ModelAttribute GetAlbumDetailRequestDto getAlbumDetailRequestDto) {

        return ResponseEntity.ok(ResponseDto.success(participationService.getAlbumDetail(getAlbumDetailRequestDto)));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<GetAlbumListResponseDto>> getAlbumList(@Valid @ModelAttribute GetAlbumListRequestDto getAlbumListRequestDto) {

        return ResponseEntity.ok(ResponseDto.success(participationService.getAlbumList(getAlbumListRequestDto)));
    }

    @GetMapping("/paticipation/{participationId}/with")
    public ResponseEntity<ResponseDto<GetParticipationWithResponseDto>> getParticipationWith(@PathVariable("participationId") Long participationId) {
        return ResponseEntity.ok(ResponseDto.success(participationService.getParticipationListWith(participationId)));
    }

}
