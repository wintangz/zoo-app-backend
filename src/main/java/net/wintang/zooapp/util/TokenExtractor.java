package net.wintang.zooapp.util;

import org.springframework.util.StringUtils;

public class TokenExtractor {

    public static String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return "";
    }
}
