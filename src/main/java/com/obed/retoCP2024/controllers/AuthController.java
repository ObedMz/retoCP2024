package com.obed.retoCP2024.controllers;

import com.obed.retoCP2024.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Generates a JWT token.
     *
     * <p>This endpoint generates a JWT token using the JWTUtil bean injected into the controller.</p>
     *
     * @return the generated JWT token as a String
     */
    @GetMapping("/token")
    public String token() {
        return jwtUtil.generateToken();
    }
}
