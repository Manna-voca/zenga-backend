package com.mannavoca.zenga.domain.praise.presentation;

import com.mannavoca.zenga.common.dto.PageResponse;
import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.ReceivedPraiseInfoResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.SendPraiseInfoResponseDto;
import com.mannavoca.zenga.domain.praise.application.service.PraiseSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/praise")
@RequiredArgsConstructor
public class PraiseGetController {
    private final PraiseSearchUseCase praiseSearchUseCase;

    @GetMapping("/todo")
    public ResponseEntity<ResponseDto<CurrentTodoPraiseResponseDto>> getMyCurrentToDoPraise(@RequestParam Long channelId)
    {
        CurrentTodoPraiseResponseDto currentTodoPraiseResponseDto = praiseSearchUseCase.getCurrentTodoPraiseAndMemberList(channelId);
        return ResponseEntity.ok(ResponseDto.of(200, "칭찬 보내기 데이터 조ㅅ에 성공했습니다.", currentTodoPraiseResponseDto));
    }

    @GetMapping("/receive")
    public ResponseEntity<ResponseDto<PageResponse<ReceivedPraiseInfoResponseDto>>> getReceivedPraiseList
            (
                    @RequestParam Long channelId,
                    @RequestParam int page
            )
    {
        PageResponse<ReceivedPraiseInfoResponseDto> pageResponse = praiseSearchUseCase.getReceivedPraiseList(channelId, page);
        return ResponseEntity.ok(ResponseDto.of(200, "받은 칭찬 리스트 조회에 성공했습니다.", pageResponse));
    }

    @GetMapping("/send")
    public ResponseEntity<ResponseDto<PageResponse<SendPraiseInfoResponseDto>>> getDonePraiseList
            (
                    @RequestParam Long channelId,
                    @RequestParam int page
            )
    {
        PageResponse<SendPraiseInfoResponseDto> pageResponse = praiseSearchUseCase.getSendPraiseList(channelId, page);
        return ResponseEntity.ok(ResponseDto.of(200, "보낸 칭찬 리스트 조회에 성공했습니다.", pageResponse));
    }
}
