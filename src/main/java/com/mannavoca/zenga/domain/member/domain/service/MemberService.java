package com.mannavoca.zenga.domain.member.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.request.UpdateMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserFindService userFindService;

    public Member getChannelMaker(Long channelId){
        return memberRepository.findMemberByChannel_IdAndLevel(channelId, LevelType.MAINTAINER)
                .orElseThrow(() -> BusinessException.of(Error.MEMBER_NOT_FOUND));
    }

    public Member findMemberByUserId(Long userId, Long channelId) {
        return memberRepository.findMemberByUser_IdAndChannel_Id(userId, channelId)
                .orElseThrow(() -> BusinessException.of(Error.MEMBER_NOT_FOUND));
    }

    public List<Member> find8RandomMembersByChannelId(Long memberId, Long channelId) {
        List<Member> membersByClubId = memberRepository.findMembersByChannelId(memberId, channelId);

        Collections.shuffle(membersByClubId);  // 리스트를 랜덤하게 섞는다.
        // 만약 리스트의 크기가 4 보다 작다면, 리스트의 모든 멤버를 반환한다.
        return membersByClubId.subList(0, Math.min(membersByClubId.size(), 8));
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(Error.MEMBER_NOT_FOUND));
    }

    public Member updatePointModal(Member member) {
        member.updatePointModal(false);
        memberRepository.save(member);
        return member;
    }

    public Member updatePraiseModal(Member member) {
        member.updatePraiseModal(false);
        memberRepository.save(member);
        return member;
    }

    /**
     * 새로운 Member 생성
     * @param user User 객체
     * @param channel Channel 객체
     * @param creatingMemberRequestDto Member 생성 요청 DTO
     * @return 생성된 Member 객체
     */
    @Transactional
    public Member createMember(final User user, final Channel channel, final CreatingMemberRequestDto creatingMemberRequestDto) {

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
     * Channel ID로 Member 조회 (페이지네이션 적용)
     *
     * @param channelId  Channel ID
     * @param cursorId   Member ID Cursor
     * @param cursorName Member Name Cursor
     * @param keyword    검색 키워드
     * @param pageable   Pageable 객체
     * @return Member Slice
     */
    public Slice<Member> findAllMemberSlicesByChannelIdAndKeyword(final Long channelId, final Long cursorId, String cursorName, final String keyword, final Pageable pageable) {
        return memberRepository.findAllMemberSlicesByChannelId(channelId, cursorId, cursorName, keyword, pageable);
    }

    /**
     * User ID와 Channel ID로 Member가 있는지 권한 검증
     * @param userId User ID
     * @param channelId Channel ID
     * @throws BusinessException Error.NOT_MEMBER_OF_CHANNEL 멤버가 아닌 경우
     */
    public void validateMemberPermissionByUserIdAndChannelId(final Long userId, final Long channelId) {
        if (!memberRepository.existsByUserIdAndChannelId(userId, channelId)) {
            throw BusinessException.of(Error.NOT_MEMBER_OF_CHANNEL);
        }
    }

    /**
     * User ID와 Channel ID로 Member가 이미 존재하는지 검증
     * @param userId User ID
     * @param channelId Channel ID
     * @throws BusinessException Error.MEMBER_ALREADY_EXISTS 이미 존재하는 경우
     */
    public void validateMemberAlreadyExistsByUserIdAndChannelId(final Long userId, final Long channelId) {
        if (memberRepository.existsByUserIdAndChannelId(userId, channelId)) {
            throw BusinessException.of(Error.MEMBER_ALREADY_EXISTS);
        }
    }

    /** Member ID가 유효한지 검증
     * @param memberId Member ID
     */
    public void validateMemberId(final Long memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            throw BusinessException.of(Error.MEMBER_NOT_FOUND);
        }
    }

    /**
     * Channel ID를 가진 Member 개수 조회
     * @param channelId Channel ID
     * @return 해당 Channel ID를 가진 Member 개수
     */
    public Long countMemberByChannelId(final Long channelId) {
        return memberRepository.countMemberByChannelId(channelId);
    }

    public List<Member> getMemberListByUserId(final Long userId) {
        return memberRepository.findAllByUser_Id(userId);
    }

    public MemberInfoResponseDto updateMember(Long userId, Long memberId, UpdateMemberRequestDto requestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        if (!member.getUser().getId().equals(userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        member.updateProfile(requestDto.getProfileImageUrl(), requestDto.getName(), requestDto.getDescription());

        return MemberMapper.mapMemberToMemberInfoResponseDto(member);
    }

}
