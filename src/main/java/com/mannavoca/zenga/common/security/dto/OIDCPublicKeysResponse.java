package com.mannavoca.zenga.common.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OIDCPublicKeysResponse {
    private List<OIDCPublicKey> keys;
}
