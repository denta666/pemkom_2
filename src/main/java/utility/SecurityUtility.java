package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SecurityUtility {

    // ================= HASHING (SATU ARAH) - untuk password =================
    public static final String SHA_256 = "SHA-256";

    public static String getHash(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritma hashing tidak ditemukan: " + algorithm, e);
        }
    }

    // ================= ENKRIPSI (DUA ARAH) - untuk UID RFID =================
    private static final String ENCRYPT_ALGORITHM = "AES";
    private static final String SECRET_KEY = "RfidSecretKey123";

    public static String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Gagal enkripsi data", e);
        }
    }

    public static String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return encryptedData;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Gagal dekripsi data", e);
        }
    }
}