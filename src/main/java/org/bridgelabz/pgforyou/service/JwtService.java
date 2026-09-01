package org.bridgelabz.pgforyou.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key used to sign and verify JWT
    private static final String SECRET_KEY = "VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9yUEdGb3J5b3U=";

    // JWT validity = 1 hour
    private static final long JWT_EXPIRATION = 1000 * 60 * 60;

    // Generate JWT
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + JWT_EXPIRATION)
                )
                .signWith(getSignInKey())
                .compact();
    }

    // Extract email from JWT
    public String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Extract any claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check whether JWT is expired
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    // Extract expiration date
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    // Create signing key
    private SecretKey getSignInKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}