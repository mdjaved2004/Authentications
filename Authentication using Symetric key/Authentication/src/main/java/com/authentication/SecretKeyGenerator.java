package com.authentication;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main1(String[] args) {

        byte[] key = new byte[32];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);

        String secretKey = Base64.getEncoder().encodeToString(key);

        System.out.println("JWT Secret Key: " + secretKey);
    }
}
