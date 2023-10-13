package net.wintang.zooapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtGenerator {

    private UserRepository userRepository;

    @Autowired
    public JwtGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Set<String> invalidatedTokens = new HashSet<>();

    public String generateToken(Authentication authentication) {
        String userId = authentication.getName();
        Collection<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + ApplicationConstants.SecurityConstants.JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean hasAuthority(String requiredAuthority) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Extract user details from the authentication object
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;

                // Check if the user has the required authority
                return userDetails.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(requiredAuthority));
            }
        }

        return false;
    }

    public boolean validateToken(String token) {
        cleanUpExpiredTokens(); // Clean up expired tokens before validation
        if (invalidatedTokens.contains(token)) {
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
        invalidatedTokens.add(token);
    }

    private void cleanUpExpiredTokens() {
        // Remove expired token from the blacklist
        invalidatedTokens.removeIf(this::isExpired);
    }

    private boolean isExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
