package net.wintang.zooapp.util;

import org.springframework.util.StringUtils;

import java.util.Base64;

public class TokenExtractor {

    public String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return "";
    }
    public String getUsername(String bearerToken) {
        //extract token here
        String token = extractToken(bearerToken);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return payload.substring(8, payload.indexOf(",")-1);
    }
}
