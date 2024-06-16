package com.obed.retoCP2024.exceptions;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class JWTExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException e) {
        String mensajeError = "Bad signature JWT: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensajeError);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException e) {
        String mensajeError = "The JWT is malformed: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensajeError);
    }
}
