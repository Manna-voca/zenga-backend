package com.mannavoca.zenga.domain.point.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.point.application.dto.response.MyTotalPointResponseDto;
import com.mannavoca.zenga.domain.point.application.dto.response.PointInfoResponseDto;
import com.mannavoca.zenga.domain.point.application.service.PointHistorySearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointController {
    private final PointHistorySearchUseCase pointHistorySearchUseCase;

    @GetMapping("/total")
    public ResponseEntity<ResponseDto<MyTotalPointResponseDto>> getTotalMyPointInfo()
    {
        MyTotalPointResponseDto totalMyPointInfo = pointHistorySearchUseCase.getTotalMyPointInfo();
        return ResponseEntity.ok(ResponseDto.success(totalMyPointInfo));
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<SliceResponse<PointInfoResponseDto>>> getMyPointHistoryInfo
            (
                    @RequestParam(required = false) Long pointId,
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        SliceResponse<PointInfoResponseDto> myPointHistoryInfo = pointHistorySearchUseCase.getMyPointHistoryInfo(pointId, pageable);
        return ResponseEntity.ok(ResponseDto.success(myPointHistoryInfo));
    }
}
