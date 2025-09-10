package com.livingnet.back;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.Map;

// clase para generacion y validación de tokens JWT

public class JwtUtil {
    public JwtUtil() {

    }

    // clave secreta (HMAC). En producción, deberías sacarla de variables de entorno.
    private static final SecretKey key = Keys.hmacShaKeyFor("JHASGDjvbadbvaisdhg29138-)(*&^VAGV2)".getBytes());

    public static String generateToken(String username) {
        Map<String, Object> commonHeaders = Map.of("alg", "HS256", "typ", "JWT");
        System.out.println("🔐 Generando token para usuario: " + username);
        return Jwts.builder()

            // 🔹 Headers
            .header()
                .add(commonHeaders)                   // headers comunes
                .add("specificHeader", "myValue")     // un header específico opcional
                .and()

            // 🔹 Claims
            .subject(username)                        // usuario (subject)
            .issuedAt(new Date())                     // fecha de creación
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // expira en 10 minutos
            .issuer("mi-app")                         // quién emitió el token
            .claim("role", "USER")                    // puedes agregar claims personalizados
            .signWith(key)                            // firmar con clave secreta
            .compact();
    }

    public static Claims parseToken(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(key)     // usamos la clave secreta para verificar la firma
                    .build()             // construir el parser
                    .parseSignedClaims(jwt) // parsea y valida (firma, expiración, etc.)
                    .getPayload();       // obtiene los claims

        } catch (JwtException ex) {
            System.out.println("❌ Token inválido: " + ex.getMessage());
            return null;
        }
    }

    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.getSubject(); // "sub" en el JWT
        }
        return null;
    }

    
}