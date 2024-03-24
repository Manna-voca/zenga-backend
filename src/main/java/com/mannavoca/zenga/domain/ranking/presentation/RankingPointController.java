package com.mannavoca.zenga.domain.ranking.presentation;

import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.application.service.RankingPointReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ranking")
@Validated
public class RankingPointController {
    private final RankingPointReadUseCase rankingPointReadUseCase;

    @GetMapping("/my")
    public ResponseEntity<MemberRankDto> findMyRank(@RequestParam("channelId") final Long channelId) {
        return ResponseEntity.ok(rankingPointReadUseCase.findMyRank(channelId));
    }
}
