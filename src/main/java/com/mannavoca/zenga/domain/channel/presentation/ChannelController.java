package com.mannavoca.zenga.domain.channel.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.request.SearchChannelMemberRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.request.UpdatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelAndMemberIdResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelOwnershipInfoResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelValidityResponseDto;
import com.mannavoca.zenga.domain.channel.application.service.ChannelCreateUseCase;
import com.mannavoca.zenga.domain.channel.application.service.ChannelDeleteUseCase;
import com.mannavoca.zenga.domain.channel.application.service.ChannelReadUseCase;
import com.mannavoca.zenga.domain.channel.application.service.ChannelUpdateUseCase;
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
    private final ChannelUpdateUseCase channelUpdateUseCase;
    private final ChannelDeleteUseCase channelDeleteUseCase;

    @GetMapping
    public ResponseEntity<ResponseDto<List<ChannelAndMemberIdResponseDto>>> getAllChannelsByUserId() {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getAllChannels()));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ChannelResponseDto>> createNewChannel(@Valid @RequestBody final CreatingChannelRequestDto creatingChannelRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(channelCreateUseCase.createChannel(creatingChannelRequestDto)));
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<ResponseDto<ChannelResponseDto>> updateChannel(@PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId, @RequestBody final UpdatingChannelRequestDto updatingChannelRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(channelUpdateUseCase.updateChannel(channelId, updatingChannelRequestDto)));
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<ResponseDto<?>> deleteChannel(@PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId) {
        channelDeleteUseCase.deleteChannel(channelId);
        return ResponseEntity.ok(ResponseDto.success());
    }


    @GetMapping("/info")
    public ResponseEntity<ResponseDto<ChannelOwnershipInfoResponseDto>> getChannelByCode(@RequestParam @NotBlank(message = "채널 코드는 필수입니다.") final String code) {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelByCode(code)));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ResponseDto<ChannelOwnershipInfoResponseDto>> getChannelById(@PathVariable @NotNull(message = "채널 ID는 필수입니다.") final Long channelId) {
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
