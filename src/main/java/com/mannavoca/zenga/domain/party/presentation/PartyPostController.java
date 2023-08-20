package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartyCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyPostController {
    private final PartyCreateUseCase partyCreateUseCase;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<CreatePartyResponseDto>> createNewParty
            (
                    @RequestParam Long channelId,
                    @RequestBody CreatePartyRequestDto createPartyRequestDto
            )
    {
        CreatePartyResponseDto createPartyResponseDto = partyCreateUseCase.createNewParty(channelId, createPartyRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "모임 생성을 성공하였습니다.", createPartyResponseDto));
    }
}
