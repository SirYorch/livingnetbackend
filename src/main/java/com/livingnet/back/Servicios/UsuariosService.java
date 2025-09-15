package com.livingnet.back.Servicios;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.livingnet.back.Gestion.UsuarioGestion;

import com.livingnet.back.Model.UsuarioModel;


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
    public List<UsuarioModel> getUsuarios() {
        List<UsuarioModel> usuarios = usuarioGestion.getUsuarios();
        for(UsuarioModel usuario : usuarios){
            usuario.setPassword(null);
        }
        return usuarios;
    }

    // agregar un nuevo Usuario
    @PostMapping
    public UsuarioModel addUsuario(@RequestBody UsuarioRequest usuario) {
        System.out.println(usuario.getMail()+" "+usuario.getConfirmPassword()+" "+usuario.getPassword()+" "+usuario.getRol());
        try{
            if(usuario.getMail() == null || usuario.getPassword() == null || usuario.getRol() == null){
                throw new IllegalArgumentException("El nombre, la contraseña y el rol son obligatorios");
            }
            if (usuario.getPassword().equals(usuario.getConfirmPassword())){
                UsuarioModel usuarioModel =new UsuarioModel();
                usuarioModel.setPassword(usuario.getPassword());
                usuarioModel.setmail(usuario.getMail());
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
        if(usuario.getPassword().equals(usuario.getConfirmPassword())){
            usuarioModel.setId(usuario.getId());
            usuarioModel.setPassword(usuario.getPassword());
            usuarioModel.setmail(usuario.getMail());
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