package com.mannavoca.zenga.domain.notification.presentation.dto.response.response;

import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationListResponseDto{

    List<NotificationDto> notificationList;

    public static GetNotificationListResponseDto of(List<Notification> notificationList) {
        GetNotificationListResponseDto responseDto = new GetNotificationListResponseDto();
        responseDto.notificationList = notificationList.stream()
                .map(NotificationDto::of)
                .collect(Collectors.toList());
        return responseDto;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NotificationDto{

        private Long id;

        private String title;

        private String content;

        private LocalDateTime createdDate;

        private boolean isCheck;

        public static NotificationDto of(Notification notification) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.id = notification.getId();
            notificationDto.title = notification.getTitle();
            notificationDto.content = notification.getContent();
            notificationDto.createdDate = notification.getCreatedDate();
            notificationDto.isCheck = notification.getIsCheck();
            return notificationDto;
        }
    }
}
