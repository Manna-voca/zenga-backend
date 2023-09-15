package com.mannavoca.zenga.domain.member.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountInfoListResponseDto;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.request.GetMemberPartyRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.request.UpdateMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberModalPermitResponseDto;
import com.mannavoca.zenga.domain.member.application.service.MemberCreateUseCase;
import com.mannavoca.zenga.domain.member.application.service.MemberModalCheckUseCase;
import com.mannavoca.zenga.domain.member.application.service.MemberReadUseCase;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapIncludingStateResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Validated
public class MemberController {
    private final MemberModalCheckUseCase memberModalCheckUseCase;
    private final MemberCreateUseCase memberCreateUseCase;
    private final MemberReadUseCase memberReadUseCase;

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<MemberInfoResponseDto>> getMemberInfoByChannelId(@RequestParam @NotNull final Long channelId) {
        return ResponseEntity.ok(ResponseDto.success(memberReadUseCase.getMemberInfoByChannelId(channelId)));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDto<MemberInfoResponseDto>> getMemberInfoByMemberId(@PathVariable("memberId") @NotNull(message = "memberId는 null일 수 없습니다.") Long memberId) {
        return ResponseEntity.ok(ResponseDto.success(memberReadUseCase.getMemberInfoByMemberId(memberId)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<MemberInfoResponseDto>>> getMemberInfoList() {
        return ResponseEntity.ok(ResponseDto.success(memberReadUseCase.getMemberInfoList()));
    }


    @PostMapping
    public ResponseEntity<ResponseDto<MemberInfoResponseDto>> createMember(@Valid @RequestBody final CreatingMemberRequestDto creatingMemberRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(memberCreateUseCase.createMember(creatingMemberRequestDto)));
    }

    @GetMapping("/modal/{channelId}")
    public ResponseEntity<ResponseDto<MemberModalPermitResponseDto>> getMemberInfo(@PathVariable Long channelId) {
        MemberModalPermitResponseDto memberModalPermit = memberModalCheckUseCase.getMemberModalPermit(channelId);
        return ResponseEntity.ok(ResponseDto.success(memberModalPermit));
    }

    @PatchMapping("/modal/praise/{channelId}")
    public ResponseEntity<ResponseDto<MemberModalPermitResponseDto>> changePraiseModalStatus(@PathVariable Long channelId) {
        MemberModalPermitResponseDto memberModalPermit = memberModalCheckUseCase.changePraiseModalStatus(channelId);
        return ResponseEntity.ok(ResponseDto.success(memberModalPermit));
    }

    @PatchMapping("/modal/point/{channelId}")
    public ResponseEntity<ResponseDto<MemberModalPermitResponseDto>> changePointModalStatus(@PathVariable Long channelId) {
        MemberModalPermitResponseDto memberModalPermit = memberModalCheckUseCase.changePointModalStatus(channelId);
        return ResponseEntity.ok(ResponseDto.success(memberModalPermit));
    }

    @GetMapping("/{memberId}/parties")
    public ResponseEntity<ResponseDto<SliceResponse<PartyTapResponseDto>>> getPartyList(
            @PathVariable @NotNull(message = "멤버 ID는 필수입니다.") final Long memberId,
            @ModelAttribute final GetMemberPartyRequestDto getMemberPartyRequestDto,
            @PageableDefault(size = 9) final Pageable pageable
    ) {
        return ResponseEntity.ok(ResponseDto.success(SliceResponse.of(memberReadUseCase.getPartyListByMemberId(memberId, getMemberPartyRequestDto.getState(), getMemberPartyRequestDto.getCursor(), pageable))));
    }

    @PostMapping("{memberId}")
    public ResponseEntity<ResponseDto<MemberInfoResponseDto>> updateMember(@PathVariable("memberId") Long memberId, @Valid @RequestBody UpdateMemberRequestDto updateMemberRequestDto) {
        Long userId = SecurityUtils.getUserId();

        return ResponseEntity.ok(ResponseDto.success(memberService.updateMember(userId, memberId, updateMemberRequestDto)));
    }

    @GetMapping("/{memberId}/parties/all")
    public ResponseEntity<ResponseDto<List<PartyTapIncludingStateResponseDto>>> getAll2PartyList(
            @PathVariable @NotNull(message = "멤버 ID는 필수입니다.") final Long memberId
    ) {
        return ResponseEntity.ok(ResponseDto.success(memberReadUseCase.getAll2PartyListByMemberId(memberId)));
    }

    @GetMapping("/{memberId}/blocks")
    public ResponseEntity<ResponseDto<BlockCountInfoListResponseDto>> getAllBlocksByMemberId(
            @PathVariable @NotNull(message = "멤버 ID는 필수입니다.") final Long memberId
    ) {
        return ResponseEntity.ok(ResponseDto.success(memberReadUseCase.getAllBlocksAndCountsByMemberId(memberId)));
    }
}