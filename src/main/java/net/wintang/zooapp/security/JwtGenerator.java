package net.wintang.zooapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtGenerator {

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Set<ExpiredToken> invalidatedTokens = new HashSet<>();

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Collection<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + ApplicationConstants.SecurityConstants.JWT_EXPIRATION);
        System.out.println("Generating Token");
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        cleanUpExpiredTokens(); // Clean up expired tokens before validation
        if (invalidatedTokens.contains(new ExpiredToken(token))) {
            return false; // Token is in the blacklist
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(new ExpiredToken(token));
    }

    private void cleanUpExpiredTokens() {
        Iterator<ExpiredToken> iterator = invalidatedTokens.iterator();
        while (iterator.hasNext()) {
            ExpiredToken expiredToken = iterator.next();
            if (expiredToken.isExpired()) {
                iterator.remove(); // Remove expired token from the blacklist
            }
        }
    }

    private static class ExpiredToken {
        private final String token;
        private final Date expiration;

        public ExpiredToken(String token) {
            this.token = token;
            this.expiration = Jwts.parser().setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256)).parseClaimsJws(token).getBody().getExpiration();
        }

        public boolean isExpired() {
            return expiration.before(new Date());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ExpiredToken that = (ExpiredToken) obj;
            return Objects.equals(token, that.token);
        }

        @Override
        public int hashCode() {
            return Objects.hash(token);
        }
    }
}
