package com.mannavoca.zenga.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IgnoredPathConst {

    public static final String[] IGNORED_PATHS = {
            "/profile", "/auth/**",
            "/reissue",
           };
}
