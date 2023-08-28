package com.mannavoca.zenga.domain.point.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.point.application.dto.response.PointInfoResponseDto;
import com.mannavoca.zenga.domain.point.domain.entity.Point;
import com.mannavoca.zenga.domain.point.domain.repository.PointRepository;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Integer countTotalPointByUserId(Long userId) {
        return pointRepository.countTotalPointByUserId(userId);
    }
    public Long countPointHistoryByCase(Long memberId, Integer pointCase) {
        return pointRepository.countPointHistoryByCase(memberId, pointCase);
    }

    public void savePointHistory(User user, Member member, int point, String description) {
        pointRepository.save(Point.toEntity(point, description, user, member));
    }

    public void savePointHistoryBulk(Map<User, Member> userMemberMap, int point, String description) {
        List<Point> pointList = userMemberMap.entrySet().stream()
                .map(entry -> Point.toEntity(point, description, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        pointRepository.saveAll(pointList);
    }

    public SliceResponse<PointInfoResponseDto> getMyPointHistoryInfo(Long userId, Long pointId, Pageable pageable) {
        return pointRepository.getMyPointHistoryInfo(userId, pointId, pageable);
    }
}
