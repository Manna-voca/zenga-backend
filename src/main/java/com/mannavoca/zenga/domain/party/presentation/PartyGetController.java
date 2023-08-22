package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyDetailInfoResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartySearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyGetController {
    private final PartySearchUseCase partySearchUseCase;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<SliceResponse<PartyTapResponseDto>>> getPartyTapList
            (
                    @RequestParam Long channelId,
                    @RequestParam(required = false) Long partyId,
                    @PageableDefault(size = 6) Pageable pageable
            )
    {
        SliceResponse<PartyTapResponseDto> partyTapResponseDtoSliceResponseDto = partySearchUseCase.searchPartyListInChannel(channelId, partyId, pageable);
        return ResponseEntity.ok(ResponseDto.success(partyTapResponseDtoSliceResponseDto));
    }

    @GetMapping("/detail/{partyId}")
    public ResponseEntity<ResponseDto<PartyDetailInfoResponseDto>> getPartyDetailInfo
            (
                @PathVariable Long partyId,
                @RequestParam Long channelId
            )
    {
        PartyDetailInfoResponseDto partyDetailInfoResponseDto = partySearchUseCase.getPartyDetailInfo(partyId, channelId);
        return ResponseEntity.ok(ResponseDto.success(partyDetailInfoResponseDto));
    }
}
