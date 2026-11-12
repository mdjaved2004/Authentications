package com.authentication.config;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.authentication.service.CustomUserDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

/*
 * SERVER A — MAIN / TRUSTED SERVER
 *
 * Responsibilities:
 * 1. Generate JWT tokens using the RSA private key.
 * 2. Verify JWT tokens using the RSA public key.
 * 3. Keep the private key secure.
 *
 * Required beans:
 * - jwtPrivateKey()
 * - jwtPublicKey()
 * - jwtEncoder()
 * - jwtDecoder()
 */


@Configuration
public class SecurityConfig {

    // 1. Configure HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthenticationConverter jwtAuthenticationConverter)
            throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter)
                        )
                );

        return httpSecurity.build();
    }

    // 2. Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
 // 6. Configure username/password authentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
    
    // 7. Create AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider authenticationProvider) {

        return new ProviderManager(authenticationProvider);
    }

    
    
 // 5. Map JWT authorities claim to Spring authorities
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("authorities");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter);

        return authenticationConverter;
    }
    
    
    
    
    
    
    
    //Load RSA private key (Server A only)
    @Bean
    public PrivateKey jwtPrivateKey(@Value("${jwt.private-key}") String privateKey) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(privateKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    //Load RSA public key
    @Bean
    public PublicKey jwtPublicKey( @Value("${jwt.public-key}") String publicKey) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);

        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    //Create JWT encoder for signing
    @Bean
    public JwtEncoder jwtEncoder(
            PrivateKey privateKey,
            PublicKey publicKey) {

        RSAKey rsaKey = new RSAKey.Builder(
                (java.security.interfaces.RSAPublicKey) publicKey)
                .privateKey(
                        (java.security.interfaces.RSAPrivateKey)
                                privateKey)
                .build();

        JWKSource<SecurityContext> jwkSource =
                new ImmutableJWKSet<>(
                        new JWKSet(rsaKey));

        return new NimbusJwtEncoder(jwkSource);
    }

    //Create JWT decoder for verification
    @Bean
    public JwtDecoder jwtDecoder(
            PublicKey publicKey,
            @Value("${jwt.issuer}") String issuer) {

        NimbusJwtDecoder decoder =
                NimbusJwtDecoder.withPublicKey(
                        (java.security.interfaces.RSAPublicKey)
                                publicKey)
                        .signatureAlgorithm(
                                SignatureAlgorithm.RS256)
                        .build();

        decoder.setJwtValidator(
                JwtValidators.createDefaultWithIssuer(issuer));

        return decoder;
    }
}
