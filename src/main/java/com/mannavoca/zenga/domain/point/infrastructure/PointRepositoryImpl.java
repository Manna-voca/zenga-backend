package com.mannavoca.zenga.domain.point.infrastructure;

import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.point.application.dto.response.PointInfoResponseDto;
import com.mannavoca.zenga.domain.point.domain.repository.PointRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.mannavoca.zenga.domain.point.domain.entity.QPoint.point1;
import static com.querydsl.core.group.GroupBy.groupBy;


@Slf4j
@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer countTotalPointByUserId(Long userId) {
        Integer totalPoint = queryFactory
                .select(point1.point.sum())
                .from(point1)
                .where(point1.user.id.eq(userId))
                .fetchOne();
        return totalPoint == null ? 0 : totalPoint;
    }

    @Override
    public Long countPointHistoryByCase(Long memberId, Integer pointCase) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monday = now.with(DayOfWeek.MONDAY);
        LocalDateTime sunday = now.with(DayOfWeek.SUNDAY).with(LocalTime.MAX);

        Long count = queryFactory
                .select(point1.count())
                .from(point1)
                .where(
                        point1.member.id.eq(memberId).and(point1.point.eq(pointCase)),
                        point1.createdDate.between(monday, sunday)
                )
                .fetchOne();
        return count == null ? 0L : count;
    }

    @Override
    public SliceResponse<PointInfoResponseDto> getMyPointHistoryInfo(Long userId, Long pointId, Pageable pageable) {
        List<PointInfoResponseDto> dtoList = queryFactory
                .select(point1)
                .from(point1)
                .where(
                        point1.user.id.eq(userId),
                        ltPointId(pointId)
                )
                .orderBy(point1.id.desc())
                .limit(pageable.getPageSize() + 1)
                .transform(groupBy(point1.id).list(Projections.fields(PointInfoResponseDto.class,
                        point1.id.as("pointId"),
                        point1.point.as("point"),
                        point1.description.as("description"),
                        point1.createdDate.as("createdAt")
                )));
        Slice<PointInfoResponseDto> pointInfoResponseDtos = checkLastPage(pageable, dtoList);
        return SliceResponse.of(pointInfoResponseDtos);
    }

    private BooleanExpression ltPointId(Long pointId) {
        return pointId != null ? point1.id.lt(pointId) : null;
    }

    private <T> Slice<T> checkLastPage(Pageable pageable, List<T> results) {
        boolean hasNext = false;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
