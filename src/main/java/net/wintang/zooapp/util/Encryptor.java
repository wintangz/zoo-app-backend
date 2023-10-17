package net.wintang.zooapp.util;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class Encryptor {

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static String calculateHMAC(String key, String data)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }
}
