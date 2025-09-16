package com.livingnet.back.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Gestion.UsuarioGestion;
import com.livingnet.back.Model.UsuarioModel;
import com.livingnet.back.Model.UsuarioRequest;
import com.livingnet.back.Model.UsuarioSend;;


// clase de servicio de usuarios, maneja las solicitudes HTTP
@RestController
@RequestMapping("/users")
public class UsuariosService {

    private UsuarioGestion usuarioGestion;

    public UsuariosService(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }

    // obtener todos los Usuarios
    @GetMapping
    public List<UsuarioSend> getUsuarios() {
        List<UsuarioModel> usuarios = usuarioGestion.getUsuarios();
        List<UsuarioSend> usuariosEnviar = new ArrayList<>(usuarios.size());
        for (int i = 0; i < usuarios.size(); i++) {
            UsuarioModel u = usuarios.get(i);
            UsuarioSend s = new UsuarioSend();
            s.setId(u.getId());
            s.setRol(u.getRol());
            s.setMail(u.getMail());
            usuariosEnviar.add(s);
        }
        return usuariosEnviar;
    }

    // agregar un nuevo Usuario
    @PostMapping
    public UsuarioModel addUsuario(@RequestBody UsuarioRequest usuario) {
        try{
            if(usuario.getMail() == null || usuario.getPassword() == null || usuario.getRol() == null){
                throw new IllegalArgumentException("El nombre, la contraseña y el rol son obligatorios");
            }
            if (usuario.getPassword().equals(usuario.getConfirmPassword()) && usuario.getId()== 0){
                UsuarioModel usuarioModel =new UsuarioModel();
                usuarioModel.setPassword(usuario.getPassword());
                usuarioModel.setMail(usuario.getMail());
                usuarioModel.setRol(usuario.getRol());
                
                return  usuarioGestion.addUsuario(usuarioModel);
            }else {
                throw new Exception("Error al confirmar contrasñas");
            }
        } catch (Exception e) {
            System.out.println("Error al agregar el Usuario: " + e);
            return null;
        }
    }

    // actualizar un reporte según su id
    @PutMapping
    public UsuarioModel updateUsuario(@RequestBody UsuarioRequest usuario ) {
        UsuarioModel usuarioModel =new UsuarioModel();
        if(usuario.getPassword().equals(usuario.getConfirmPassword()) && usuario.getId() != 0){
            usuarioModel.setId(usuario.getId());
            usuarioModel.setPassword(usuario.getPassword());
            usuarioModel.setMail(usuario.getMail());
            usuarioModel.setRol(usuario.getRol());
            
            usuarioGestion.updateUsuario(usuarioModel);
        }else {
            return null;
        }
        return usuarioModel;
        
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public boolean deleteUsuario(@PathVariable Long id) {
        boolean dato  =  usuarioGestion.deleteUsuario(id);
        System.out.println(dato);
        return dato;
    }


}