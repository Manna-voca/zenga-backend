package com.mannavoca.zenga.domain.ranking.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.ranking.application.dto.ChannelMemberRankDto;
import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.application.service.RankingPointReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ranking")
@Validated
public class RankingPointController {
    private final RankingPointReadUseCase rankingPointReadUseCase;

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<MemberRankDto>> findMyRank(
            @Valid @NotNull @RequestParam("channelId") final Long channelId
    ) {
        return ResponseEntity.ok(ResponseDto.success(rankingPointReadUseCase.findMyRank(channelId)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<ChannelMemberRankDto>> findChannelMemberRank(
            @Valid @NotNull @RequestParam("channelId") final Long channelId
    ) {
        return ResponseEntity.ok(ResponseDto.success(rankingPointReadUseCase.findChannelMemberRanks(channelId)));
    }

}
