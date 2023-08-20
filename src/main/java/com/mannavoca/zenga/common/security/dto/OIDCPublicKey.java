package com.mannavoca.zenga.common.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OIDCPublicKey {
    private String kid;
    private String kty;
    private String alg;
    private String use;
    private String n;
    private String e;
}
