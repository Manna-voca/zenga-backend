package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.ResponseCode.ResponseCode;
import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CompletePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.EditPartyInfoRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CompletePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartyUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyPatchController {
    private final PartyUpdateUseCase partyUpdateUseCase;

    @PatchMapping("/edit")
    public ResponseEntity<ResponseDto<CreatePartyResponseDto>> editPartyInfo
            (
                    @RequestBody EditPartyInfoRequestDto editPartyInfoRequestDto
            )
    {
        CreatePartyResponseDto createPartyResponseDto = partyUpdateUseCase.editPartyInfo(editPartyInfoRequestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.PARTY_UPDATED, createPartyResponseDto));
    }

    @PatchMapping("/finish")
    public ResponseEntity<ResponseDto<CompletePartyResponseDto>> uploadPartyCardImage
            (
                    @RequestBody CompletePartyRequestDto completePartyRequestDto
            )
    {
        CompletePartyResponseDto completePartyResponseDto = partyUpdateUseCase.uploadPartyCardAndComplete(completePartyRequestDto);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.PARTY_CARD_CREATED, completePartyResponseDto));
    }
}
