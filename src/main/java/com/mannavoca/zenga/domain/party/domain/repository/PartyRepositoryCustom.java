package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.party.domain.entity.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PartyRepositoryCustom {
    Slice<Party> findPartyListByChannelId(Long channelId, Long partyId, Pageable pageable);
}
