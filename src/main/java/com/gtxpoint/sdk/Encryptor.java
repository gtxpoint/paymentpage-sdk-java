package com.gtxpoint.sdk;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * Class Cipher
 */
public class Encryptor
{
    private static final String ENCRYPT_ALGO = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH_BYTE = 32;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private final Cipher cipher;
    protected String delimiter = "::";
    protected byte[] iv;

    private Encryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(ENCRYPT_ALGO);
        this.iv = new byte[cipher.getBlockSize()];
    }

    public static Encryptor getInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return new Encryptor();
    }

    /**
     * @param paymentLink
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public String encrypt(String paymentLink, String encryptKey) throws Exception {

        IvParameterSpec ivParams = (IvParameterSpec) getIVParam();

        byte[] paddedKey = new byte[KEY_LENGTH_BYTE];
        byte[] byteKey = encryptKey.getBytes(UTF_8);
        System.arraycopy(byteKey, 0, paddedKey, 0, byteKey.length);

        SecretKeySpec key = new SecretKeySpec(paddedKey, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        String encrypted = toBase64(cipher.doFinal(paymentLink.getBytes(UTF_8)));

        return toBase64((encrypted + delimiter + toBase64(iv)).getBytes());
    }

    /**
     * @return IvParameterSpec iv param
     */
    private AlgorithmParameterSpec getIVParam() {
        SecureRandom randomSecureRandom = new SecureRandom();
        randomSecureRandom.nextBytes(iv);

        return new IvParameterSpec(iv);
    }

    /**
     * @param message String
     * @return String
     */
    private String toBase64(byte[] message) {
        return Base64.getEncoder().encodeToString(message);
    }
}