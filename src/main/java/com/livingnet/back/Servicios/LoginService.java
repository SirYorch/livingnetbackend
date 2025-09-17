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
@RequestMapping("")
public class LoginService {

    private final UsuarioGestion usuarioGestion;

    public LoginService(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioModel usuario) {
        try {
            if(usuario.getMail() ==null  && usuario.getPassword() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(Collections.singletonMap("error", "Empty Credentials"));    
            }
            UsuarioModel usuarioEncontrado = usuarioGestion.buscarPorEmailYPassword(usuario.getMail(), usuario.getPassword());    
            if (usuarioEncontrado != null) {
                String token = JwtUtil.generateToken(usuarioEncontrado);
                
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                throw new Exception("Error de validacion");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }

}