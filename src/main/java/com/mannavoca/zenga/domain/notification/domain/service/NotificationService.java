package com.mannavoca.zenga.domain.notification.domain.service;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.notification.presentation.dto.response.response.GetNotificationListResponseDto;
import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import com.mannavoca.zenga.domain.notification.domain.repository.NotificationRepository;
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

        if (Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        List<Notification> notificationList = notificationRepository.findAllByMember(member);

        return GetNotificationListResponseDto.of(notificationList);
    }

    public void checkNotification(Long notificationId) {

            Long userId = SecurityUtils.getUserId();
            Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

            if (Objects.equals(notification.getMember().getUser().getId(), userId)) {
                throw BusinessException.of(Error.NOT_AUTHORIZED);
            }

            notification.check();
    }

    public void createPraiseNotification(Member member, Praise praise) {

        Notification notification = Notification.createNotification("누군가 나를 칭찬했어요!", member, "칭찬: " + praise.getDescription());
        notificationRepository.save(notification);
    }

    public void createFullPartyNotification(Member member, Party party) {

            Notification notification = Notification.createNotification("모임이 꽉 찼어요!", member, "모임: " + party.getTitle());
            notificationRepository.save(notification);
    }

    public void createPartyCompletedNotification(Member member, Party party) {

            Notification notification = Notification.createNotification("모임이 완료되었어요 카드를 만들어보세요!", member, "모임: " + party.getTitle());
            notificationRepository.save(notification);
    }

    public void createCardNotification(Member member, Party party) {

            Notification notification = Notification.createNotification("카드가 만들어졌어요!", member, "모임: " + party.getTitle());
            notificationRepository.save(notification);
    }

    public void createChannelOpenedNotification(Member member, Channel channel) {

            Notification notification = Notification.createNotification("채널이 개설되었어요!", member, "채널: " + channel.getName());
            notificationRepository.save(notification);
    }

    public void checkAllNotification(Long memberId) {
        Long userId = SecurityUtils.getUserId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        if (Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        List<Notification> notificationList = notificationRepository.findAllByMember(member);

        for (Notification notification : notificationList) {
            notification.check();
        }
    }

    public HasUncheckedNotificationResponseDto hasUncheckedNotification(Long memberId) {

        Long userId = SecurityUtils.getUserId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
        if (Objects.equals(member.getUser().getId(), userId)) {
            throw BusinessException.of(Error.NOT_AUTHORIZED);
        }

        return HasUncheckedNotificationResponseDto.of(notificationRepository.hasUncheckedNotification(member.getId()));

    }
}
