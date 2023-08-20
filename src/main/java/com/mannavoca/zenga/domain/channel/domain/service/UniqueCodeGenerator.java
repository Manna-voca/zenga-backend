package com.mannavoca.zenga.domain.channel.domain.service;

import java.time.Instant;

public class UniqueCodeGenerator {
    public static String generateCodeForChannel(long channelId) {
        // 현재 시간을 밀리초로 가져옵니다.
        long currentTimeMillis = Instant.now().toEpochMilli();

        // 채널의 PK와 현재 시간을 조합합니다.
        long combined = channelId * 10000000000L + currentTimeMillis;

        // 16진수로 변환한 뒤, 마지막 8자리만 잘라내서 반환합니다.
        String hexString = Long.toHexString(combined).toUpperCase();
        return hexString.length() > 8 ? hexString.substring(hexString.length() - 8) : hexString;
    }

}
