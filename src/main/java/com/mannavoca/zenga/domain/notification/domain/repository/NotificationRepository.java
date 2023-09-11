package com.mannavoca.zenga.domain.notification.domain.repository;


import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
    List<Notification> findAllByMember(Member member);

    void updateCheckByMemberIdAndIsCheck(Long memberId);
}
