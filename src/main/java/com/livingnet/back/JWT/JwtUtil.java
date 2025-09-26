package com.livingnet.back.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;

import com.livingnet.back.Model.UsuarioModel;

import java.util.Date;
import java.util.Map;

/**
 * Utilidad para la generación y parseo de tokens JWT.
 * Maneja la creación de tokens basados en roles de usuario y la validación de tokens recibidos.
 */
public class JwtUtil {
    /**
     * Constructor por defecto de JwtUtil.
     */
    public JwtUtil() {

    }
    
    //clave jwt habrá de ser cambiada para las validaciones
    private static final SecretKey key = Keys.hmacShaKeyFor(
    System.getenv("JWT_SECRET").getBytes()
);


    /**
     * Genera un token JWT para un usuario específico.
     * El tiempo de expiración varía según el rol del usuario.
     * @param user El modelo de usuario para el cual generar el token.
     * @return El token JWT generado como string.
     */
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

    /**
     * Parsea y valida un token JWT.
     * Verifica la firma, expiración y obtiene los claims.
     * @param jwt El token JWT a parsear.
     * @return Los claims del token si es válido, null si es inválido.
     */
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

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     * @param token El token JWT del cual extraer el username.
     * @return El username si el token es válido, null en caso contrario.
     */
    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    
}