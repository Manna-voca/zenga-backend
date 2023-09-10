package com.mannavoca.zenga.domain.channel.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.request.SearchChannelMemberRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelAndMemberIdResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelValidityResponseDto;
import com.mannavoca.zenga.domain.channel.application.service.ChannelCreateUseCase;
import com.mannavoca.zenga.domain.channel.application.service.ChannelReadUseCase;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
@Validated
public class ChannelController {
    private final ChannelCreateUseCase channelCreateUseCase;
    private final ChannelReadUseCase channelReadUseCase;

    @GetMapping
    public ResponseEntity<ResponseDto<List<ChannelAndMemberIdResponseDto>>> getAllChannelsByUserId() {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getAllChannels()));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ChannelResponseDto>> createNewChannel(@Valid @RequestBody final CreatingChannelRequestDto creatingChannelRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(channelCreateUseCase.createChannel(creatingChannelRequestDto)));
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<ChannelResponseDto>> getChannelByCode(@RequestParam @NotBlank(message = "채널 코드는 필수입니다.") final String code) {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelByCode(code)));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ResponseDto<ChannelResponseDto>> getChannelById(@PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId) {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelById(channelId)));
    }

    @GetMapping("/{channelId}/validity")
    public ResponseEntity<ResponseDto<ChannelValidityResponseDto>> getChannelValidityById(@PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId){
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelValidityById(channelId)));
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<SliceResponse<MemberInfoResponseDto>> getAllMembersByChannelId(
            @PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId,
            @ModelAttribute final SearchChannelMemberRequestDto searchChannelMemberRequestDto,
            @PageableDefault final Pageable pageable
    ) {
        return ResponseEntity.ok(SliceResponse.of(channelReadUseCase.searchAllMembersByChannelId(channelId, searchChannelMemberRequestDto.getCursor(), searchChannelMemberRequestDto.getKeyword(), pageable)));
    }

}
