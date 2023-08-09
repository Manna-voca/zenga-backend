package com.mannavoca.zenga.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionUtils {
//    @Value("${aes.cbc.iv}")
//    private String initVector;
//
//    @Value("${aes.cbc.secret-key}")
//    private String secretKey;
//
//    public String encrypt(String value) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (Exception ex) {
//            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public String passwordEncrypt(String email, String password) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//            String value = email+password;
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (Exception ex) {
//            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public String decrypt(String encrypted) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//
//            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
//
//            return new String(original);
//        } catch (Exception ex) {
//            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
//        }
//    }
}
