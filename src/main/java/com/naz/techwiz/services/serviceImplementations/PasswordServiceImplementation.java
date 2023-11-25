package com.naz.techwiz.services.serviceImplementations;

import com.naz.techwiz.exceptions.OurException;
import com.naz.techwiz.services.PasswordService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;


@Service
public class PasswordServiceImplementation implements PasswordService {

    private static final String ENCRYPTED_KEY = generateKey();
    private int T_LEN = 256;

    private static String generateKey() {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
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

    @Override
    public String passwordDecryption(String encryptedPassword) {
        try {
            SecretKeyFactory factory2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(ENCRYPTED_KEY.toCharArray(), ENCRYPTED_KEY.getBytes(), 65536, 256);
            SecretKey tmp = factory2.generateSecret(spec);
            SecretKey key = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
}












//    private  String decrypt(String encryptedMessage) throws Exception{
//        byte[] messageInBytes = decode(encryptedMessage);
//        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
//        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
//        decryptionCipher.init(Cipher.DECRYPT_MODE, T_LEN, spec );
//        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
//        return new String(decryptedBytes);
//    }