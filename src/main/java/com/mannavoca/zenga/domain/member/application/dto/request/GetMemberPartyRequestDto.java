package com.mannavoca.zenga.domain.member.application.dto.request;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class GetMemberPartyRequestDto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "모임 상태값은 필수입니다. (RECRUITING, IN_PROGRESS, COMPLETED)")
    private State state;

    private Long cursor;
}
