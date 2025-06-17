package com.shrinitha.dochub.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final String secret = Base64.getEncoder().encodeToString("thisIsaSecretKey+jwtSecretKeyForBetterPassw=".getBytes());
    private final long expiration = 1000 * 60 * 60; // 1 hour
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();

        Field secretField = JwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, secret);

        Field expirationField = JwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, expiration);
    }

    @Test
    void shouldGenerateAndValidateToken() {
        String username = "john_doe";

        String token = jwtUtil.generateToken(username);
        assertNotNull(token, "Token should not be null");

        assertTrue(jwtUtil.validateToken(token), "Token should be valid");
        assertEquals(username, jwtUtil.extractUsername(token), "Extracted username should match");
    }

    @Test
    void shouldFailValidationForInvalidToken() {
        String invalidToken = "this.is.not.a.valid.token";
        assertFalse(jwtUtil.validateToken(invalidToken), "Validation should fail for invalid token");
    }

    @Test
    void shouldFailValidationForExpiredToken() throws Exception {
        // Set very short expiration
        Field expirationField = JwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, 1); // 1 millisecond

        String token = jwtUtil.generateToken("expired_user");

        // Sleep to ensure expiration
        Thread.sleep(10);

        assertFalse(jwtUtil.validateToken(token), "Expired token should be invalid");
    }
}
