package com.mannavoca.zenga.domain.party.infrastructure;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.PartyRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import com.mannavoca.zenga.common.exception.Error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mannavoca.zenga.domain.party.domain.entity.QParticipation.participation;
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

    @Override
    public List<Party> find2EachPartiesByMemberId(Long memberId) {
        List<Party> result = new ArrayList<>();

        result.addAll( // 모임 모집중
                queryFactory.selectFrom(party)
                        .innerJoin(party.participationList, participation)
                        .where(party.isOpen.isTrue(), party.cardImageUrl.isNull(), participation.member.id.eq(memberId))
                        .orderBy(party.id.desc())
                        .limit(2)
                        .fetch()
        );

        result.addAll( // 모임 진행중
                queryFactory.selectFrom(party)
                        .innerJoin(party.participationList, participation)
                        .where(party.isOpen.isFalse(), party.cardImageUrl.isNull(), participation.member.id.eq(memberId))
                        .orderBy(party.id.desc())
                        .limit(2)
                        .fetch()
        );

        result.addAll( // 모임 완료
                queryFactory.selectFrom(party)
                        .innerJoin(party.participationList, participation)
                        .where(party.cardImageUrl.isNotNull(), participation.member.id.eq(memberId))
                        .orderBy(party.id.desc())
                        .limit(2)
                        .fetch()
        );

        return result;
    }

    @Override
    public Slice<Party> findPartiesByMemberIdAndState(Long memberId, State state, Long partyId, Pageable pageable) {
        List<Party> partyList =
                queryFactory.selectFrom(party)
                .innerJoin(party.participationList, participation)
                .where(getConditionByState(state), participation.member.id.eq(memberId), ltPartyId(partyId))
                .orderBy(party.id.desc())
                .limit(pageable.getPageSize() + 1)
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

    private BooleanExpression getConditionByState(State state) {
        switch (state) {
            case RECRUITING:
                return party.isOpen.isTrue().and(party.cardImageUrl.isNull());
            case IN_PROGRESS:
                return party.isOpen.isFalse().and(party.cardImageUrl.isNull());
            case COMPLETED:
                return party.cardImageUrl.isNotNull();
            default:
                throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
        }
    }
}
