package com.mannavoca.zenga.domain.praise.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.request.ChooseMemberPraiseRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.request.OpenMemberPraiseRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.request.PraiseChannelIdRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.service.PraiseUpdateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/praise")
@RequiredArgsConstructor
public class PraisePatchController {
    private final PraiseUpdateUseCase praiseUpdateUseCase;

    @PatchMapping("/todo")
    public ResponseEntity<ResponseDto<CurrentTodoPraiseResponseDto>> getMyCurrentToDoPraiseShuffle(@RequestBody PraiseChannelIdRequestDto praiseChannelIdRequestDto)
    {
        CurrentTodoPraiseResponseDto currentTodoPraiseResponseDto = praiseUpdateUseCase.getAgainCurrentTodoPraiseAndMemberList(praiseChannelIdRequestDto.getChannelId());
        return ResponseEntity.ok(ResponseDto.success(currentTodoPraiseResponseDto));
    }

    @PatchMapping("/choice")
    public ResponseEntity<ResponseDto<Void>> chooseMemberPraise
            (
                    @RequestBody ChooseMemberPraiseRequestDto chooseMemberPraiseRequestDto
            )
    {
        praiseUpdateUseCase.choosePraise(chooseMemberPraiseRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }

    @PatchMapping("/open")
    public ResponseEntity<ResponseDto<Void>> openMemberPraise
            (
                    @RequestBody OpenMemberPraiseRequestDto openMemberPraiseRequestDto
            )
    {
        praiseUpdateUseCase.openMemberPraise(openMemberPraiseRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
