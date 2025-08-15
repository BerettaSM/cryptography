package com.ramon.crypt.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CryptographyService {

    private static final int AES_BLOCK_SIZE = 16;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String TRANSFORMATION_NAME = "AES/CBC/PKCS5Padding";

    private final SecretKey secretKey;

    @SuppressWarnings("UseSpecificCatch")
    public String encrypt(String data) {
        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION_NAME);
            final IvParameterSpec ivSpec = createIv(AES_BLOCK_SIZE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            final byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET));
            final byte[] combined = combineEncryptedBytesWithIv(encryptedBytes, ivSpec.getIV());
            return Base64.getEncoder().encodeToString(combined);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public String decrypt(String encryptedData) {
        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION_NAME);
            final byte[] combined = Base64.getDecoder().decode(encryptedData);
            final IvParameterSpec ivSpec = extractIv(combined, AES_BLOCK_SIZE);
            final int totalEncryptedBytes = combined.length - ivSpec.getIV().length;
            final byte[] encryptedBytes = extractEncryptedBytes(combined, totalEncryptedBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            final byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, CHARSET);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private IvParameterSpec createIv(int size) {
        final SecureRandom random = new SecureRandom();
        final byte[] iv = new byte[size];
        random.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private IvParameterSpec extractIv(byte[] combined, int size) {
        final byte[] iv = new byte[size];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        return new IvParameterSpec(iv);
    }

    private byte[] extractEncryptedBytes(byte[] combined, int size) {
        final byte[] encryptedBytes = new byte[size];
        System.arraycopy(combined, combined.length - size, encryptedBytes, 0, encryptedBytes.length);
        return encryptedBytes;
    }

    private byte[] combineEncryptedBytesWithIv(byte[] encryptedBytes, byte[] iv) {
        final byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
        return combined;
    }

}
