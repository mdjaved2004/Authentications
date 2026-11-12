package com.authentication;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class SecretKeyGeneratorRSA {
	public static void main1(String[] args) throws Exception {

        // Generate RSA key pair
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        // Private key in PKCS#8 DER format, Base64 encoded
        String privateKey = Base64.getEncoder()
                .encodeToString(
                        keyPair.getPrivate().getEncoded());

        // Public key in X.509 DER format, Base64 encoded
        String publicKey = Base64.getEncoder()
                .encodeToString(
                        keyPair.getPublic().getEncoded());

        System.out.println("PRIVATE KEY:");
        System.out.println(privateKey);

        System.out.println("\nPUBLIC KEY:");
        System.out.println(publicKey);
    }

}
