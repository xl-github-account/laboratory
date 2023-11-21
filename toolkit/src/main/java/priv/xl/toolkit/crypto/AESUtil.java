package priv.xl.toolkit.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES加解密工具类
 *
 * @author lei.xu
 * @since 2023/8/21 3:20 PM
 */
public class AESUtil {

    public static String encrypt(String key, String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            // 初始化Cipher为加密模式，并将密钥注入到算法中
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            // 加密文本
            byte[] ciphertextBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            // 生成密文（Base64编码）
            return Base64.getEncoder().encodeToString(ciphertextBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String key, String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            // 初始化Cipher为解密模式，并将密钥注入到算法中
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            // 获取加密文本
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            // 解密
            byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);
            return new String(plaintextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String key = "kaifayongdemiyao";

        String c = encrypt(key, "985236");
        String p = decrypt(key, c);
        System.out.println("密文：" + c);
        System.out.println("明文：" + p);
    }

}
