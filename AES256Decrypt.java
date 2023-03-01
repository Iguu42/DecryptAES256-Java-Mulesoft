package org.example;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AES256Decrypt{
    public static String initVector = "0000000000000000";

    public static String decrypt(byte[] key, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
            String hex = DatatypeConverter.printHexBinary(original);
            String result = hex.substring(32);
            byte[] hexBytes = DatatypeConverter.parseHexBinary(result);
            String text = new String(hexBytes, "UTF-8");
            return new String(text);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.print(ex.getMessage());
        }
        return null;
    }
    public static byte[] stringToByteArray(final String hex) throws Exception {
        int NumberChars = hex.length();
        byte[] bytes = new byte[NumberChars / 2];
        for (int i = 0; i < NumberChars; i += 2) {
            int y = Integer.parseInt(hex.substring(i,  i + 2), 16);
            bytes[i/2] = (byte)(y & 0xFF);
        }
        return bytes;
    }
    public static String decryptPayloadtoString(String key, String encrypted) throws Exception {
        byte[] key1 = stringToByteArray(key);
        String stringReturn = decrypt(key1,encrypted);
        return stringReturn;
    }
}
