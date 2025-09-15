package com.livingnet.back.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;

import com.livingnet.back.Model.UsuarioModel;

import java.util.Date;
import java.util.Map;

// clase para generacion y validación de tokens JWT

public class JwtUtil {
    public JwtUtil() {

    }
    
    private static final SecretKey key = Keys.hmacShaKeyFor("JHASGDjvbadbvaisdhg29138-)(*&^VAGV2)".getBytes());

    public static String generateToken(UsuarioModel user) {
        Map<String, Object> commonHeaders = Map.of("alg", "HS256", "typ", "JWT");
        int tiempoRol = 0;
        System.out.println("Generando token para usuario: " + user.getRol());
        if( user.getRol().equals("ADMINISTRADOR")){
            tiempoRol = 120; // 2 horas
        } else if ( user.getRol().equals("TECNICO")){
            tiempoRol = 10; // 10 minutos
        } else if ( user.getRol().equals("SECRETARIA")){
            tiempoRol = 60 * 4; //4 horas
        }
        return Jwts.builder()

            // 🔹 Headers
            .header()
                .add(commonHeaders)                   // headers comunes
                .and()
            // 🔹 Claims
            .subject(user.getmail())                        // usuario (subject)
            .issuedAt(new Date())                     // fecha de creación
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * tiempoRol)) // expira dependiento el tipo de usuario
            .issuer("reportsapp")                         // quién emitió el token
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