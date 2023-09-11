package com.mannavoca.zenga.domain.notification.domain.repository;

import com.mannavoca.zenga.domain.notification.domain.entity.QNotification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateCheckByMemberIdAndIsCheck(Long memberId) {
        jpaQueryFactory.update(QNotification.notification)
                .where(QNotification.notification.member.id.eq(memberId))
                .set(QNotification.notification.isCheck, true)
                .execute();
    }
}
