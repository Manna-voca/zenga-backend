package com.mannavoca.zenga.domain.praise.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mannavoca.zenga.domain.praise.domain.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, CandidateRepositoryCustom {
    List<Candidate> findCandidatesByMember_Id(Long memberId);

    @Query("SELECT distinct c FROM Candidate c JOIN FETCH c.candidate WHERE c.member.id = :memberId")
    List<Candidate> findCandidateMembersByMember_Id(@Param("memberId") Long memberId);

    Boolean existsByMember_Id(Long memberId);

}
