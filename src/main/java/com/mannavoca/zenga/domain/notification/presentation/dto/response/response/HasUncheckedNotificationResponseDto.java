package com.mannavoca.zenga.domain.notification.presentation.dto.response.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HasUncheckedNotificationResponseDto {

    private boolean hasUncheckedNotification;

    public static HasUncheckedNotificationResponseDto of(boolean hasUncheckedNotification) {
        HasUncheckedNotificationResponseDto responseDto = new HasUncheckedNotificationResponseDto();
        responseDto.hasUncheckedNotification = hasUncheckedNotification;
        return responseDto;
    }
}
