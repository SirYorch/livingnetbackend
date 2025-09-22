package com.livingnet.back.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;

import com.livingnet.back.Model.UsuarioModel;

import java.util.Date;
import java.util.Map;

// clase para generacion y parseo de tokens JWT
public class JwtUtil {
    public JwtUtil() {

    }
    
    //clave jwt habrá de ser cambiada para las validaciones
    private static final SecretKey key = Keys.hmacShaKeyFor(
    System.getenv("JWT_SECRET").getBytes()
);


    //clase para generación del token
    public static String generateToken(UsuarioModel user) {
        Map<String, Object> commonHeaders = Map.of("alg", "HS256", "typ", "JWT"); // encriptación
        int tiempoRol = 0;
        
        if( user.getRol().equals(UsuarioModel.ROL_ADMIN)){
            tiempoRol = 120; // 2 horas
        } else if ( user.getRol().equals(UsuarioModel.ROL_TECNICO)){
            tiempoRol = 15; // 15 minutos
        } else if ( user.getRol().equals(UsuarioModel.ROL_SECRETARIO)){
            tiempoRol = 60 * 4; //4 horas
        } // se utilizan los roles para la asignación de tiempos de sesión.


        return Jwts.builder()

            .header()
                .add(commonHeaders)
                .and()
            .subject(user.getMail())                        // usuario (subject) 
            .issuedAt(new Date())                     // fecha de creación
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * tiempoRol)) //tiempo en minutos, se multiplica por cada usuario expira dependiento el tipo de usuario
            .issuer("reportsapp")                         
            .signWith(key)                            // firmar con clave secreta
            .compact();
    }

    //clase para validacion del usuarios utilizando el token jwt que se envía en el header de la solicitud http
    public static Claims parseToken(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(key)     // usamos la clave secreta para verificar la firma
                    .build()             // construir el parser
                    .parseSignedClaims(jwt) // parsea y valida (firma, expiración, etc.)
                    .getPayload();       // obtiene los claims

        } catch (JwtException ex) {
            System.out.println("Token inválido: " + ex.getMessage());
            return null;
        }
    }

    // micrométodo para sacar el usuario de token parseado
    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    
}