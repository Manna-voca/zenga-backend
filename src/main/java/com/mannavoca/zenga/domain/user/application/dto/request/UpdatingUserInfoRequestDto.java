package com.mannavoca.zenga.domain.user.application.dto.request;

import com.mannavoca.zenga.domain.user.domain.entity.enumType.GenderType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UpdatingUserInfoRequestDto {

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "성별을 입력해주세요.")
    private GenderType gender;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요.")
    private String birthDate;

}
