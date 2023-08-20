package com.mannavoca.zenga.domain.praise.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.service.PraiseUpdateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/praise")
@RequiredArgsConstructor
public class PraisePatchController {
    private final PraiseUpdateUseCase praiseUpdateUseCase;

    @PatchMapping("/todo")
    public ResponseEntity<ResponseDto<CurrentTodoPraiseResponseDto>> getMyCurrentToDoPraiseShuffle(@RequestParam Long channelId)
    {
        CurrentTodoPraiseResponseDto currentTodoPraiseResponseDto = praiseUpdateUseCase.getAgainCurrentTodoPraiseAndMemberList(channelId);
        return ResponseEntity.ok(ResponseDto.of(200, "칭찬 보내기 셔플 데이터 조회에 성공했습니다.", currentTodoPraiseResponseDto));
    }
}
