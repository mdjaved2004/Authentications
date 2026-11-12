//package com.example.demo.config;
//
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtValidators;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.example.demo.service.CustomUserDetailsService;
//
//
//
///*
// * SERVER B — SECONDARY / UNTRUSTED SERVER
// *
// * Responsibilities:
// * 1. Verify JWT tokens using the RSA public key.
// * 2. Validate the JWT signature and claims.
// * 3. Access protected APIs after successful token validation.
// *
// * Required beans:
// * - jwtPublicKey()
// * - jwtDecoder()
// *
// * IMPORTANT:
// * - Do NOT configure jwtPrivateKey().
// * - Do NOT configure jwtEncoder().
// * - Do NOT generate or sign JWT tokens on this server.
// */
//
//
////@Configuration
//public class SecurityConfigForOtherServer {
//
//    //Configure stateless HTTP security
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity httpSecurity,
//            JwtAuthenticationConverter jwtAuthenticationConverter)
//            throws Exception {
//
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/register", "/login")
//                        .permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS)
//                )
//
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt(jwt ->
//                                jwt.jwtAuthenticationConverter(
//                                        jwtAuthenticationConverter)
//                        )
//                );
//
//        return httpSecurity.build();
//    }
//
//    //Password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    
//    //Configure username/password authentication
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(
//            CustomUserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder) {
//
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider(userDetailsService);
//
//        provider.setPasswordEncoder(passwordEncoder);
//
//        return provider;
//    }
//    
//    //Create AuthenticationManager
//    @Bean
//    public AuthenticationManager authenticationManager(
//            DaoAuthenticationProvider authenticationProvider) {
//
//        return new ProviderManager(authenticationProvider);
//    }
//
//    
// //Map JWT authorities claim to Spring authorities
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//
//        JwtGrantedAuthoritiesConverter authoritiesConverter =
//                new JwtGrantedAuthoritiesConverter();
//
//        authoritiesConverter.setAuthoritiesClaimName("authorities");
//        authoritiesConverter.setAuthorityPrefix("");
//
//        JwtAuthenticationConverter authenticationConverter =
//                new JwtAuthenticationConverter();
//
//        authenticationConverter.setJwtGrantedAuthoritiesConverter(
//                authoritiesConverter);
//
//        return authenticationConverter;
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    //Load RSA public key only
//    @Bean
//    public PublicKey jwtPublicKey(
//            @Value("${jwt.public-key}") String publicKey)
//            throws Exception {
//
//        byte[] decodedKey =
//                Base64.getDecoder().decode(publicKey);
//
//        X509EncodedKeySpec keySpec =
//                new X509EncodedKeySpec(decodedKey);
//
//        return KeyFactory.getInstance("RSA")
//                .generatePublic(keySpec);
//    }
//
//    //Create JWT decoder for signature verification
//    @Bean
//    public JwtDecoder jwtDecoder(
//            PublicKey publicKey,
//            @Value("${jwt.issuer}") String issuer) {
//
//        NimbusJwtDecoder decoder =
//                NimbusJwtDecoder.withPublicKey(
//                        (java.security.interfaces.RSAPublicKey)
//                                publicKey)
//                        .signatureAlgorithm(
//                                SignatureAlgorithm.RS256)
//                        .build();
//
//        decoder.setJwtValidator(
//                JwtValidators.createDefaultWithIssuer(issuer));
//
//        return decoder;
//    }
//
//    
//
//    
//
//   
//}


