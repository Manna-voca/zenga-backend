package com.mannavoca.zenga.domain.member.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findMemberByUser_IdAndChannel_Id(Long userId, Long channelId);

    List<Member> findAllByUser_Id(Long userId);

    Optional<Member> findMemberByChannel_IdAndLevel(Long channelId, LevelType levelType);
}
