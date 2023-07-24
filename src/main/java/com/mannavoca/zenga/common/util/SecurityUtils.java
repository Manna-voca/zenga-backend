package com.mannavoca.zenga.common.util;

import com.mannavoca.zenga.common.consts.ApplicationConst;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.security.jwt.JwtAuthenticationToken;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SecurityUtils {

    public static String validateHeaderAndGetToken(final String headerValue){
        return Optional.ofNullable(headerValue)
                .filter(header -> header.startsWith(ApplicationConst.JWT_AUTHORIZATION_TYPE))
                .filter(StringUtils::hasText)
                .map(header -> header.replace(ApplicationConst.JWT_AUTHORIZATION_TYPE, ""))
                .orElseThrow(() -> BusinessException.of(Error.INVALID_TOKEN));
    }

    public static String getUserUUID(){
        return (String)getAuthentication().getPrincipal();
    }

    private static JwtAuthenticationToken getAuthentication(){
        return (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
    }

}
