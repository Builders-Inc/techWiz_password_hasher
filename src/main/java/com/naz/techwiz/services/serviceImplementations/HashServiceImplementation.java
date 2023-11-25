package com.naz.techwiz.services.serviceImplementations;

import com.naz.techwiz.exceptions.OurException;
import com.naz.techwiz.services.HashService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class HashServiceImplementation implements HashService {

    private static final String ENCRYPTED_KEY = decrepitKey();

    private static String decrepitKey() {

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
    @Override
    public String passwordEncryption(String password) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(ENCRYPTED_KEY.toCharArray(), ENCRYPTED_KEY.getBytes(), 65536, 256);
            SecretKey temp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new OurException("You are doing something wrong");
        }
    }
}