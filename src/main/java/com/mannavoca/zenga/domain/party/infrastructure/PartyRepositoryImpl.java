package com.mannavoca.zenga.domain.party.infrastructure;

import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.PartyRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.mannavoca.zenga.domain.party.domain.entity.QParty.party;

@Repository
@RequiredArgsConstructor
public class PartyRepositoryImpl implements PartyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Party> findPartyListByChannelId(Long channelId, Long partyId, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        List<Party> partyList = queryFactory
                .select(party)
                .from(party)
                .where(
                        party.channel.id.eq(channelId),
                        party.isOpen.isTrue(),
                        party.partyDate.isNull().or(party.partyDate.after(now)),
                        ltPartyId(partyId)
                )
                .orderBy(party.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();
        return checkLastPage(pageable, partyList);
    }

    private BooleanExpression ltPartyId(Long partyId) {
        return partyId != null ? party.id.lt(partyId) : null;
    }

    private Slice<Party> checkLastPage(Pageable pageable, List<Party> results) {
        boolean hasNext = false;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
