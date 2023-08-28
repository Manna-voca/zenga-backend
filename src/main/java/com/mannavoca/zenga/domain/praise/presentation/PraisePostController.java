package com.mannavoca.zenga.domain.praise.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.request.PraiseChannelIdRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.service.PraiseCreateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/praise")
@RequiredArgsConstructor
public class PraisePostController {
    private final PraiseCreateUseCase praiseCreateUseCase;

    @PostMapping("/todo")
    public ResponseEntity<ResponseDto<CurrentTodoPraiseResponseDto>> getMyCurrentToDoPraise(@RequestBody PraiseChannelIdRequestDto praiseChannelIdRequestDto)
    {
        CurrentTodoPraiseResponseDto currentTodoPraiseResponseDto = praiseCreateUseCase.getCurrentTodoPraiseAndMemberList(praiseChannelIdRequestDto.getChannelId());
        return ResponseEntity.ok(ResponseDto.success(currentTodoPraiseResponseDto));
    }

}
