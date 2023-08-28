package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findCandidatesByMember_Id(Long memberId);

    @Query("SELECT distinct c FROM Candidate c JOIN FETCH c.candidate WHERE c.member.id = :memberId")
    List<Candidate> findCandidateMembersByMember_Id(@Param("memberId") Long memberId);

    Boolean existsByMember_Id(Long memberId);

}
