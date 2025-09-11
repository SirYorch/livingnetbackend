package com.livingnet.back.Servicios;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.livingnet.back.Gestion.UsuarioGestion;
import com.livingnet.back.JWT.JwtUtil;
import com.livingnet.back.Model.UsuarioModel;


// clase de servicio de usuarios, maneja las solicitudes HTTP
@RestController
@RequestMapping("/usuarios")
public class UsuarioService {

    private final UsuarioGestion usuarioGestion;

    public UsuarioService(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioModel usuario) {
        try {
            System.out.println("Intento de login para: " + usuario.getName() + " con password: " + usuario.getPassword());
            UsuarioModel usuarioEncontrado = usuarioGestion.buscarPorEmailYPassword(usuario.getName(), usuario.getPassword());
            
            if (usuarioEncontrado != null) {
                String token = JwtUtil.generateToken(usuarioEncontrado.getName());
                
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", usuarioEncontrado.getName());
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(Collections.singletonMap("error", "Credenciales incorrectas"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }


   @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("Entró al endpoint protegido");
        return ResponseEntity.ok("Hola mundo, tu token es válido");
    }


}