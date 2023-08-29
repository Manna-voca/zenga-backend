package com.mannavoca.zenga.domain.member.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserFindService userFindService;

    public Member findMemberByUserId(Long userId, Long channelId) {
        return memberRepository.findMemberByUser_IdAndChannel_Id(userId, channelId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }

    public List<Member> find8RandomMembersByChannelId(Long memberId, Long channelId) {
        List<Member> membersByClubId = memberRepository.findMembersByChannelId(memberId, channelId);

        Collections.shuffle(membersByClubId);  // 리스트를 랜덤하게 섞는다.
        // 만약 리스트의 크기가 4 보다 작다면, 리스트의 모든 멤버를 반환한다.
        return membersByClubId.subList(0, Math.min(membersByClubId.size(), 8));
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }

    /**
     * 새로운 Member 생성
     * @param user User 객체
     * @param channel Channel 객체
     * @param creatingMemberRequestDto Member 생성 요청 DTO
     * @return 생성된 Member 객체
     */
    @Transactional
    public Member createMember(User user, Channel channel, CreatingMemberRequestDto creatingMemberRequestDto) {

        Member member = Member.builder()
                .nickname(creatingMemberRequestDto.getNickname())
                .profileImageUrl(creatingMemberRequestDto.getProfileImageUrl())
                .introduction(creatingMemberRequestDto.getIntroduction())
                .level(creatingMemberRequestDto.getLevel())
                .user(user)
                .channel(channel)
                .build();

        return memberRepository.save(member);
    }

    /**
     * Channel ID로 Member 조회
     * @param channelId Channel ID
     * @return Member 리스트
     */
    public List<Member> findAllMembersByChannelId(Long channelId) {
        return memberRepository.findAllMembersByChannelId(channelId);
    }

    /** Member ID가 유효한지 검증
     * @param memberId Member ID
     */
    public void validateMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw BusinessException.of(Error.MEMBER_NOT_FOUND);
        }
    }
}
