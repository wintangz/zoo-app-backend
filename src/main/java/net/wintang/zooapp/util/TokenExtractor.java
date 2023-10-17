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
}
