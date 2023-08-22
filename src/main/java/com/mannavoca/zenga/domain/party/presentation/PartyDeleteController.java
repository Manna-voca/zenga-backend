package com.mannavoca.zenga.domain.party.presentation;

import com.mannavoca.zenga.common.ResponseCode.ResponseCode;
import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.party.application.service.PartyDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/party")
@RequiredArgsConstructor
public class PartyDeleteController {
    private final PartyDeleteUseCase partyDeleteUseCase;

    @DeleteMapping("/cancel")
    public ResponseEntity<ResponseDto<Void>> cancelParty
            (
                    @RequestParam Long channelId,
                    @RequestParam Long partyId
            )
    {
        partyDeleteUseCase.cancelParty(channelId, partyId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.PARTY_DELETED, null));
    }

    @DeleteMapping("/apply/cancel")
    public ResponseEntity<ResponseDto<Void>> applyCancelParty
            (
                    @RequestParam Long channelId,
                    @RequestParam Long partyId
            )
    {
        partyDeleteUseCase.applyCancelParty(channelId, partyId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.PARTY_JOIN_CANCELED, null));
    }
}
