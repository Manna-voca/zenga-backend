package com.mannavoca.zenga.domain.notification.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.notification.presentation.dto.response.response.GetNotificationListResponseDto;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResponseDto<GetNotificationListResponseDto>> getNotificationListByMemberId(@PathVariable("memberId") Long memberId) {

        GetNotificationListResponseDto responseDto = notificationService.getNotificationListByMemberId(memberId);

        return ResponseEntity.ok(ResponseDto.success(responseDto));
    }

    @PutMapping("/{nofiticaionId}/check")
    public ResponseEntity<ResponseDto> checkNotification(@PathVariable("nofiticaionId") Long notificationId) {

        notificationService.checkNotification(notificationId);

        return ResponseEntity.ok(ResponseDto.success());
    }
}
