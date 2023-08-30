package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findParticipationByParty_Id(Long partyId);

    boolean existsByParty_IdAndMember_Id(Long partyId, Long memberId);

    void deleteByParty_IdAndMember_Id(Long partyId, Long memberId);

    void deleteAllByParty_Id(Long partyId);

    Long countByParty_Id(Long partyId);

    @Query("select p.member.id from Participation p where p.party.id = ?1 and p.isMaker = true")
    Long findPartyMarkerId(Long partyId);
}
