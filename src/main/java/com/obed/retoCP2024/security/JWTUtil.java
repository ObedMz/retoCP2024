package com.obed.retoCP2024.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * Service class for JWT token generation and validation.
 */
@Service
public class JWTUtil {
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    /**
     * Generates a JWT token.
     *
     * @return JWT token as a string
     */
    public String generateToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(now.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * Checks if a JWT token is valid.
     *
     * @param token JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }
    /**
     * Retrieves the user from a JWT token.
     *
     * @param token JWT token from which to retrieve the user
     * @return user extracted from the token, or null if token is malformed
     */
    public String getUserFromToken(String token) {
        try {
            return getClaim(token, Claims::getSubject);
        } catch (MalformedJwtException e) {
            return null;
        }
    }

    /**
     * Parses and retrieves claims from a JWT token.
     *
     * @param token JWT token from which to retrieve claims
     * @return claims parsed from the token
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Retrieves a specific claim from a JWT token.
     *
     * @param token          JWT token from which to retrieve the claim
     * @param claimsResolver function to resolve the desired claim from Claims
     * @param <T>            type of the claim
     * @return resolved claim
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the expiration date from a JWT token.
     *
     * @param token JWT token from which to retrieve the expiration date
     * @return expiration date of the token
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

}
