package com.mannavoca.zenga.domain.point.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.point.domain.service.PointService;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PointPolicyUseCase {
    private final PointService pointService;
    private final NotificationService notificationService;
    // User 의 전체 포인트를 조회
    public Integer getUserTotalPoint(User user) {
        return pointService.countTotalPointByUserId(user.getId());
    }

    // Member 가 칭찬할 경우 50점 추가 (일주일에 10번)
    public void accumulatePointByPraise(User user, Member member, String channelName) {
        if (isAvailableAccumulatePointByCase(member.getId(), 50)) {
            pointService.savePointHistory(user, member, 50, "[" + channelName + "] 칭찬 포인트가 적립되었어요!");
        }
    }

    // Member 가 모임을 완료할 경우 100점 추가 (일주일에 3번)
    public void accumulatePointByParty(List<Member> memberList, Party party) {
        // 이 로직에서 같은 모임에 다수의 Member 가 같은 User 를 가질 수 있으므로 distinct 처리
        Map<User, Member> userMemberMap = new LinkedHashMap<>();
        memberList.stream()
                .filter(member -> isAvailableAccumulatePointByCase(member.getId(), 100))
                .forEach(member -> userMemberMap.putIfAbsent(member.getUser(), member));
        pointService.savePointHistoryBulk(userMemberMap, 100, "[" + party.getChannel().getName() + "] 모임에 참여하여 포인트가 적립되었어요!");

        memberList.forEach(
                member -> notificationService.createPartyCompletedNotification(member, party)
        );
    }

    // Member 가 포인트를 사용할 경우
    public void usePoint(Member member, String channelName) {
        if (pointService.countTotalPointByUserId(member.getUser().getId()) >= 300) {
            pointService.savePointHistory(member.getUser(), member, -300, "[" + channelName + "] 받은 칭찬을 확인했어요!");
        } else {
            throw BusinessException.of(Error.NOT_ENOUGH_POINT);
        }
    }

    // Member 의 금주의 적립 내역 조회해서 가능한지 여부 확인
    private Boolean isAvailableAccumulatePointByCase(Long memberId, int pointCase) {
        if (pointCase == 50) { // 칭찬으로 얻는 경우
            return pointService.countPointHistoryByCase(memberId, pointCase) < 10;
        } else if (pointCase == 100) { // 모임으로 얻는 경우
            return pointService.countPointHistoryByCase(memberId, pointCase) < 3;
        } else {
            return false;
        }
    }
}
