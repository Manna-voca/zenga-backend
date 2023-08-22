package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CompletePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.EditPartyInfoRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CompletePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartyUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyPatchController {
    private final PartyUpdateUseCase partyUpdateUseCase;

    @PatchMapping("/edit")
    public ResponseEntity<ResponseDto<CreatePartyResponseDto>> editPartyInfo
            (
                    @RequestParam Long channelId,
                    @RequestBody EditPartyInfoRequestDto editPartyInfoRequestDto
            )
    {
        CreatePartyResponseDto createPartyResponseDto = partyUpdateUseCase.editPartyInfo(channelId, editPartyInfoRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "모임 정보 수정을 성공하였습니다.", createPartyResponseDto));
    }

    @PatchMapping("/finish")
    public ResponseEntity<ResponseDto<CompletePartyResponseDto>> uploadPartyCardImage
            (
                    @RequestParam Long channelId,
                    @RequestBody CompletePartyRequestDto completePartyRequestDto
            )
    {
        CompletePartyResponseDto completePartyResponseDto = partyUpdateUseCase.uploadPartyCardAndComplete(channelId, completePartyRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "모임 카드 생성에 성공하였습니다.", completePartyResponseDto));
    }
}
