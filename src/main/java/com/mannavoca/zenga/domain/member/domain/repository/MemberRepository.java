package com.mannavoca.zenga.domain.member.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
