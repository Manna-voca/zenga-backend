package com.mannavoca.zenga.domain.user.application.dto.request;

import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserOnboardingRequestDto {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;
    @NotNull(message = "성별을 입력해주세요.")
    private GenderType gender;
    @DateTimeFormat(pattern = "yyyy-M-d")
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;

}
