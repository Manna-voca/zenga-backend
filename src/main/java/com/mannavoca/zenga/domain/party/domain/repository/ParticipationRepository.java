package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findParticipationByParty_Id(Long partyId);
}