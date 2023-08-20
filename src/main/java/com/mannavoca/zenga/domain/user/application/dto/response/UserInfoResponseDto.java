package com.mannavoca.zenga.domain.user.application.dto.response;

import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String name;
    private GenderType gender;
    private LocalDate birth;

    public String getBirth() {
        return Optional.ofNullable(birth)
                .map(b -> b.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orElse(null);

    }
}
