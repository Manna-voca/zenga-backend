package com.mannavoca.zenga.domain.point.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.point.application.dto.response.MyTotalPointResponseDto;
import com.mannavoca.zenga.domain.point.application.dto.response.PointInfoResponseDto;
import com.mannavoca.zenga.domain.point.domain.service.PointService;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PointHistorySearchUseCase {
    private final UserUtils userUtils;
    private final PointService pointService;

    public MyTotalPointResponseDto getTotalMyPointInfo() {
        User user = userUtils.getUser();
        Integer totalPoint = pointService.countTotalPointByUserId(user.getId());
        return MyTotalPointResponseDto.builder()
                .point(totalPoint)
                .build();
    }

    public SliceResponse<PointInfoResponseDto> getMyPointHistoryInfo(Long pointId, Pageable pageable) {
        User user = userUtils.getUser();
        return pointService.getMyPointHistoryInfo(user.getId(), pointId, pageable);
    }
}
