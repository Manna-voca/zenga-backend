package com.mannavoca.zenga.domain.point.domain.repository;

import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.point.application.dto.response.PointInfoResponseDto;
import org.springframework.data.domain.Pageable;

public interface PointRepositoryCustom {
    Integer countTotalPointByUserId(Long userId);
    Long countPointHistoryByCase(Long memberId, Integer pointCase);
    SliceResponse<PointInfoResponseDto> getMyPointHistoryInfo(Long userId, Long pointId, Pageable pageable);
}
