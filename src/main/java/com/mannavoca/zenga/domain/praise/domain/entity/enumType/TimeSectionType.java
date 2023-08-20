package com.mannavoca.zenga.domain.praise.domain.entity.enumType;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public enum TimeSectionType implements CodeValue {
    A("a", "6-10", LocalTime.of(6, 0), LocalTime.of(9, 59)),
    B("b", "10-14", LocalTime.of(10, 0), LocalTime.of(13, 59)),
    C("c", "14-18", LocalTime.of(14, 0), LocalTime.of(17, 59)),
    D("d", "18-22", LocalTime.of(18, 0), LocalTime.of(21, 59)),
    E("e", "22-2", LocalTime.of(22, 0), LocalTime.of(1, 59)),
    F("f","2-6", LocalTime.of(2, 0), LocalTime.of(5, 59));

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
            if (section == E) { // Special case because it spans over midnight
                if ((time.isAfter(section.startTime) || time.isBefore(section.endTime))
                        && (time.isBefore(section.endTime.plusMinutes(1)))) {
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
