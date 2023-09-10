package com.mannavoca.zenga.domain.praise.domain.entity.enumType;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public enum TimeSectionType implements CodeValue {
    A("a", "0-4", LocalTime.of(0, 0), LocalTime.of(3, 59)),
    B("b", "4-8", LocalTime.of(4, 0), LocalTime.of(7, 59)),
    C("c", "8-12", LocalTime.of(8, 0), LocalTime.of(11, 59)),
    D("d", "12-16", LocalTime.of(12, 0), LocalTime.of(15, 59)),
    E("e", "16-20", LocalTime.of(16, 0), LocalTime.of(19, 59)),

    F("f","20-24", LocalTime.of(20, 0), LocalTime.of(23, 59));

    private String code;
    private String value;
    private LocalTime startTime;
    private LocalTime endTime;

    TimeSectionType(String code, String value, LocalTime startTime, LocalTime endTime) {
        this.code = code;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeSectionType from(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        for (TimeSectionType section : TimeSectionType.values()) {
            if (section == F) { // Special case because it spans over midnight
                if (time.isAfter(section.startTime) || time.isBefore(section.endTime.plusMinutes(1))) {
                    return section;
                }
            } else if (time.isAfter(section.startTime) && time.isBefore(section.endTime.plusMinutes(1))) {
                return section;
            }
        }

        throw BusinessException.of(Error.DATA_NOT_FOUND);
    }

    public static List<TimeSectionType> getSectionTypes() {
        return List.of(TimeSectionType.values());
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
