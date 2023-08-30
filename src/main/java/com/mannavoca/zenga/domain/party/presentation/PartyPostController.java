package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.ResponseCode.ResponseCode;
import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.request.ApplyPartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartyCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyPostController {
    private final PartyCreateUseCase partyCreateUseCase;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<CreatePartyResponseDto>> createNewParty
            (
                    @RequestBody CreatePartyRequestDto createPartyRequestDto
            )
    {
        CreatePartyResponseDto createPartyResponseDto = partyCreateUseCase.createNewParty(createPartyRequestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.PARTY_CREATED, createPartyResponseDto));
    }

    @PostMapping("/apply")
    public ResponseEntity<ResponseDto<Void>> applyParty
            (
                    @RequestBody ApplyPartyRequestDto applyPartyRequestDto
            )
    {
        partyCreateUseCase.applyParty(applyPartyRequestDto);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
