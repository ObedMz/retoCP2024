package com.obed.retoCP2024.exceptions;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

/**
 * Global exception handler for JWT-related exceptions.
 */
@RestControllerAdvice
public class JWTExceptionHandler {
    /**
     * Handles SignatureException for JWT.
     *
     * <p>Returns a ResponseEntity with HTTP status 401 (Unauthorized) and an error message indicating
     * a bad JWT signature.</p>
     *
     * @param e the SignatureException thrown
     * @return ResponseEntity with HTTP status 401 (Unauthorized) and an error message
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException e) {
        String mensajeError = "Bad signature JWT: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensajeError);
    }

    /**
     * Handles MalformedJwtException for JWT.
     *
     * <p>Returns a ResponseEntity with HTTP status 400 (Bad Request) and an error message indicating
     * that the JWT is malformed.</p>
     *
     * @param e the MalformedJwtException thrown
     * @return ResponseEntity with HTTP status 400 (Bad Request) and an error message
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException e) {
        String mensajeError = "The JWT is malformed: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensajeError);
    }
}
