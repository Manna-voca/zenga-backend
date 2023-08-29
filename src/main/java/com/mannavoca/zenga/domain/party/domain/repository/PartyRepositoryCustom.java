package com.mannavoca.zenga.domain.party.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PartyRepositoryCustom {
    Slice<Party> findPartyListByChannelId(Long channelId, Long partyId, Pageable pageable);

    List<Party> find2EachPartiesByMemberId(Long memberId);

    Slice<Party> findPartiesByMemberIdAndState(Long memberId, State state, Long partyIdCursor, Pageable pageable);
}
