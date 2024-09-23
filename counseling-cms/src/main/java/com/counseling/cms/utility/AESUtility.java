package com.counseling.cms.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtility {
    private static final String ALGORITHM = "AES";
    private static final String CHARSET = StandardCharsets.UTF_8.name();
    
    // 암호화
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET));
        return Base64UrlSafe.encode(encryptedBytes); // URL 안전한 Base64 인코딩
    }

    // 복호화
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        byte[] decodedBytes = Base64UrlSafe.decode(encryptedData); // URL 안전한 Base64 디코딩
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, CHARSET);
    }
    
    public static SecretKey getSecretKeyFromBase64(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }
}
