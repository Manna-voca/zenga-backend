package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.party.domain.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long>, PartyRepositoryCustom {
}
