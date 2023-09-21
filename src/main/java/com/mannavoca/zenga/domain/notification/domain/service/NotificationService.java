package com.mannavoca.zenga.domain.notification.domain.service;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import com.mannavoca.zenga.domain.notification.domain.repository.NotificationRepository;
import com.mannavoca.zenga.domain.notification.presentation.dto.response.response.GetNotificationListResponseDto;
import com.mannavoca.zenga.domain.notification.presentation.dto.response.response.HasUncheckedNotificationResponseDto;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MemberRepository memberRepository;

    private final NotificationRepository notificationRepository;

    public GetNotificationListResponseDto getNotificationListByMemberId(Long memberId) {

        Long userId = SecurityUtils.getUserId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        if (!Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        List<Notification> notificationList = notificationRepository.findAllByMemberOrderByIdDesc(member);

        return GetNotificationListResponseDto.of(notificationList);
    }

    public void checkNotification(Long notificationId) {

            Long userId = SecurityUtils.getUserId();
            Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

            if (!Objects.equals(notification.getMember().getUser().getId(), userId)) {
                throw BusinessException.of(Error.NOT_AUTHORIZED);
            }

            notification.check();
            notificationRepository.save(notification);
    }

    public void createPraiseNotification(Member member, Praise praise) {

        Notification notification = Notification.createNotification("누군가 나를 칭찬했어요!", member, "칭찬: " + praise.getDescription());
        notificationRepository.save(notification);
    }

    public void createFullPartyNotification(Member member, Party party) {

        int maxTitleLength = 12;
        String title = party.getTitle().length() > maxTitleLength ? party.getTitle().substring(0, maxTitleLength) + "..." : party.getTitle();

        Notification notification = Notification.createNotification("‘" + title + "’" + " 의 모집 인원이 다 찼어요!", member, "‘모집완료’ 버튼을 누르고 모임을 시작해봐요");
        notificationRepository.save(notification);
    }

    public void createPartyCompletedNotification(Member member, Party party) {

            Notification notification = Notification.createNotification("모임을 완료했어요!", member, "모임: " + party.getTitle());
            notificationRepository.save(notification);
    }

    public void createCardMakingNotification(Member maker, Party party) {

        int maxTitleLength = 13;
        String title = party.getTitle().length() > maxTitleLength ? party.getTitle().substring(0, maxTitleLength) + "..." : party.getTitle();

        Notification notification = Notification.createNotification("‘" + title + "’ 의 모임 카드를 만들 수 있어요!", maker, "모임을 완료한 후 사진을 등록해서 우리만의 카드를 만들어봐요");
        notificationRepository.save(notification);
    }

    public void createCardNotification(Member member, Party party) {
        int maxTitleLength = 13;
        String title = party.getTitle().length() > maxTitleLength ? party.getTitle().substring(0, maxTitleLength) + "..." : party.getTitle();
        Notification notification = Notification.createNotification("‘" + title + "’ 모임의 카드가 만들어졌어요!", member, "마이페이지_앨범에서 새롭게 만들어진 카드를 확인할 수 있어요");
        notificationRepository.save(notification);
    }

    public void createChannelOpenedNotification(Member member, Channel channel) {

            Notification notification = Notification.createNotification("지금부터 ’" + channel.getName() + "’ 채널을 시작해보세요!", member, "최소 인원 10명이 모여 모든 서비스가 활성화 되었어요");
            notificationRepository.save(notification);
    }

    public void checkAllNotification(Long memberId) {
        Long userId = SecurityUtils.getUserId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        if (!Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        List<Notification> notificationList = notificationRepository.findAllByMemberOrderByIdDesc(member);

        for (Notification notification : notificationList) {
            notification.check();
        }
        notificationRepository.saveAll(notificationList);
    }

    public HasUncheckedNotificationResponseDto hasUncheckedNotification(Long memberId) {

        Long userId = SecurityUtils.getUserId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
        if (!Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        return HasUncheckedNotificationResponseDto.of(notificationRepository.hasUncheckedNotification(member.getId()));

    }
}
