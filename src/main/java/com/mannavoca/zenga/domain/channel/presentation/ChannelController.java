package com.mannavoca.zenga.domain.channel.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.service.ChannelCreateUseCase;
import com.mannavoca.zenga.domain.channel.application.service.ChannelReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelCreateUseCase channelCreateUseCase;
    private final ChannelReadUseCase channelReadUseCase;

    @GetMapping
    public ResponseEntity<ResponseDto<List<ChannelResponseDto>>> getAllChannelsByUserId() {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getAllChannelsByUserId(SecurityUtils.getUserId())));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ChannelResponseDto>> createNewChannel(@RequestBody CreatingChannelRequestDto creatingChannelRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(channelCreateUseCase.createChannel(creatingChannelRequestDto)));
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<ChannelResponseDto>> getChannelById(@RequestParam String code) {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelByCode(code)));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ResponseDto<ChannelResponseDto>> getChannelById(@PathVariable Long channelId) {
        return ResponseEntity.ok(ResponseDto.success(channelReadUseCase.getChannelById(channelId)));
    }
}
