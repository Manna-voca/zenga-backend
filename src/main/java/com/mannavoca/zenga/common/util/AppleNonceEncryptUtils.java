package com.mannavoca.zenga.common.util;


import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppleNonceEncryptUtils {

    private static final String ENCRYPT_ALGORITHM = "SHA-256";
    private static final String FORMAT_CODE = "%02x";

    public static String encrypt(String value) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            byte[] digest = sha256.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format(FORMAT_CODE, b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw BusinessException.of(Error.APPLE_OIDC_FAILED5);
        }
    }
}
