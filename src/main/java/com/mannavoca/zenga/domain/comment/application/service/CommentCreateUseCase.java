package com.mannavoca.zenga.domain.comment.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.mannavoca.zenga.domain.comment.domain.service.CommentService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CommentCreateUseCase {
    private final UserUtils userUtils;
    private final MemberService memberService;
    private final PartyService partyService;
    private final CommentService commentService;

    public void createComment(CommentWriteRequestDto commentWriteRequestDto) {
        memberService.validateChannelMember(SecurityUtils.getUserId(), commentWriteRequestDto.getChannelId());

        Member writer = userUtils.getMember(commentWriteRequestDto.getChannelId());
        Party party = partyService.getPartyById(commentWriteRequestDto.getPartyId());
        commentService.createComment(writer, party, commentWriteRequestDto);
    }
}
