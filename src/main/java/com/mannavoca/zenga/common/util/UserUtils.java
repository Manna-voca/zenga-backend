package com.mannavoca.zenga.common.util;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserFindService userFindService;
    private final MemberService memberService;

    public User getUser(){
        final Long userId = SecurityUtils.getUserId();
        return userFindService.findById(userId);
    }

    public Member getMember(Long channelId) {
        final Long userId = SecurityUtils.getUserId();
        return memberService.findMemberByUserId(userId, channelId);
    }
}
