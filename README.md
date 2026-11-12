# 🔐 Authentication Module – JWT Implementation

## 📌 About the Project

This project demonstrates a secure authentication and authorization system using **Spring Boot, Spring Security, and JWT (JSON Web Token)**.

It provides user registration, login, password encryption, role-based authorization, and stateless authentication using JWT.

Two JWT implementations are covered:

- **HS256:** Symmetric-key authentication.
- **RS256:** Asymmetric-key authentication using RSA.

## 🛠️ Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT (Nimbus JOSE + JWT)
- Maven

---

# 🔹 Common Implementation (Both HS256 and RS256)

The following features and configurations are common to both implementations.

## ✨ Key Features

### 1. User Registration
- User registration using `RegisterService`.
- Duplicate username and email validation.
- Default USER role assignment.
- Password encryption using BCrypt.
- DTO-based request validation.
- Transaction management using `@Transactional`.

### 2. User Authentication
- Login using username and password.
- Authentication using `AuthenticationManager`.
- Authentication provider using `DaoAuthenticationProvider`.
- User details retrieval using `CustomUserDetailsService`.
- Password verification using `PasswordEncoder`.

### 3. Role-Based Authorization
- USER and ADMIN roles.
- Role management using `RoleEntity` and `RoleRepository`.
- Role-based access control.
- JWT authorities mapping.

### 4. Spring Security Configuration
- `SecurityFilterChain` configuration.
- Public registration and login endpoints.
- Authentication required for protected endpoints.
- CSRF disabled for stateless REST API authentication.
- Session management using `SessionCreationPolicy.STATELESS`.

### 5. JWT Authentication
- JWT generation after successful login.
- JWT validation for protected endpoints.
- Token-based authentication.
- Issuer and expiration validation.
- Authorities included in JWT claims.

### 6. Exception Handling
- Centralized exception handling using `@RestControllerAdvice`.
- Custom error responses.
- Authentication and authorization error handling.

## 🔄 Authentication Flow

1. User registers with username, email, and password.
2. Password is encrypted using BCrypt.
3. User logs in with credentials.
4. Spring Security authenticates the user.
5. JWT is generated after successful authentication.
6. Client stores the access token.
7. Client sends the token in the Authorization header.
8. Spring Security validates the JWT.
9. Access is granted based on user roles and authorities.

---

# 🔑 Implementation 1: Symmetric-Key (HS256 Algorithm)

## 📌 Overview

HS256 (HMAC using SHA-256) is a symmetric-key JWT signing algorithm that uses the same secret key for both token generation and verification.

## 🔐 Key Configuration

- Uses a single secret key.
- The same key signs and verifies JWT tokens.
- The secret key must remain confidential.

## ⚙️ Implementation

- JWT generation using `NimbusJwtEncoder`.
- JWT verification using `NimbusJwtDecoder`.
- HMAC-SHA256 signing algorithm.
- Secret key configured using application properties.

## 🔄 Working Flow

1. Generate a JWT using the secret key.
2. Sign the token using HS256.
3. Send the JWT to the client.
4. Verify the JWT using the same secret key.
5. Grant access if the token is valid.

---

# 🔐 Implementation 2: Asymmetric-Key (RSA with RS256 Algorithm)

## 📌 Overview

RS256 (RSA Signature with SHA-256) is an asymmetric-key JWT signing algorithm that uses a private key for signing and a public key for verification.

## 🔐 Key Configuration

- Uses two different keys: Private Key and Public Key.
- Private key is used for JWT signing.
- Public key is used for JWT verification.
- Private key must remain confidential.
- Public key can be shared with trusted services.

## ⚙️ Implementation

- RSA key generation.
- Private and public key configuration.
- `RSAKey` for RSA key representation.
- `NimbusJwtEncoder` for JWT generation.
- `NimbusJwtDecoder` for JWT verification.
- RS256 signing algorithm.

## 🔄 Working Flow

1. Generate an RSA private and public key pair.
2. Generate a JWT using the private key.
3. Sign the token using RS256.
4. Send the JWT to the client.
5. Verify the JWT using the public key.
6. Grant access if the token is valid.

## 🏢 Server Architecture

### Server A – Main / Trusted Server

- Generates JWT tokens using the RSA private key.
- Verifies JWT tokens using the RSA public key.
- Keeps the private key secure.

### Server B – Resource Server

- Receives JWT tokens from clients.
- Verifies JWT signatures using the public key.
- Does not require the private key.

---

# 📊 Difference Between HS256 and RS256

| Feature | HS256 | RS256 |
|---|---|---|
| Key Type | Symmetric | Asymmetric |
| Keys Used | One secret key | Private and Public keys |
| Signing | Secret key | Private key |
| Verification | Secret key | Public key |
| Algorithm | HMAC-SHA256 | RSA-SHA256 |
| Key Sharing | Secret must be shared securely | Public key can be distributed |
| Multi-server use | Requires shared secret | Supports separate signing and verification servers |

---

## 🎯 Learning Objectives

- Understand JWT authentication and authorization.
- Implement Spring Security with JWT.
- Understand symmetric and asymmetric cryptography.
- Generate and validate JWT tokens.
- Implement role-based access control.
- Understand secure authentication in REST APIs.
- Compare HS256 and RS256 implementations.

