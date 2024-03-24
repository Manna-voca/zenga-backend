package com.mannavoca.zenga.domain.ranking.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class RankingPointHistoryDto {
    private String date;
    private String contents;
    private Integer point;

    public RankingPointHistoryDto(LocalDateTime dateTime, String contents, Integer point) {
        this.date = dateTime.format(DateTimeFormatter.ofPattern("yy.mm.dd"));
        this.contents = contents;
        this.point = point;
    }
}
