package com.example.myapplication;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class CryptoUtils {

    // Генерирует случайный ключ (для учебных целей)
    public static String generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // 128 бит
            SecretKey secretKey = keyGen.generateKey();
            return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String base64Key, String data) throws Exception {
        byte[] keyBytes = Base64.decode(base64Key, Base64.DEFAULT);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public static String decrypt(String base64Key, String encryptedData) throws Exception {
        byte[] keyBytes = Base64.decode(base64Key, Base64.DEFAULT);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }
}
