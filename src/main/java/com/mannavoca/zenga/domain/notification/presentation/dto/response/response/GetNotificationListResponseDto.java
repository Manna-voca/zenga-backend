package com.mannavoca.zenga.domain.notification.presentation.dto.response.response;

import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationListResponseDto {

    List<NotificationDto> notificationList;

    public static GetNotificationListResponseDto of(List<Notification> notificationList) {
        GetNotificationListResponseDto responseDto = new GetNotificationListResponseDto();
        responseDto.notificationList = notificationList.stream()
                .map(NotificationDto::of)
                .collect(Collectors.toList());
        return responseDto;
    }


    public static class NotificationDto {

        private Long id;

        private String content;

        public static NotificationDto of(Notification notification) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.id = notification.getId();
            notificationDto.content = notification.getContent();
            return notificationDto;
        }
    }
}
