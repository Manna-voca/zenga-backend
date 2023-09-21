package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long>, ParticipationRepositoryCustom {
    List<Participation> findParticipationByParty_Id(Long partyId);

    boolean existsByParty_IdAndMember_Id(Long partyId, Long memberId);

    void deleteByParty_IdAndMember_Id(Long partyId, Long memberId);

    void deleteAllByParty_Id(Long partyId);

    Long countByParty_Id(Long partyId);

    @Query("select p.member.id from Participation p where p.party.id = ?1 and p.isMaker = true")
    Long findPartyMarkerId(Long partyId);

    Optional<Participation> findByParty_IdAndMember_Id(Long partyId, Long memberId);
  
    List<Participation> findAllByMemberAndAlbumCreatedDateIsNotNullOrderByAlbumCreatedDateDesc(Member member);

    Long countByMember_Id(Long memberId);
}
