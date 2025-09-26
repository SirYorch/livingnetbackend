package com.livingnet.back.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Gestion.UsuarioGestion;
import com.livingnet.back.Model.UsuarioModel;
import com.livingnet.back.Model.UsuarioRequest;
import com.livingnet.back.Model.UsuarioSend;;


/**
 * Servicio REST para la gestión de usuarios.
 * 
 * Expone los endpoints bajo la ruta /users.
 * 
 * Métodos:
 * - GET /users: Devuelve la lista de usuarios en formato UsuarioSend (sin exponer contraseñas).
 * - POST /users: Crea un nuevo usuario, validando que las contraseñas coincidan y que no tenga ID asignado.
 * - PUT /users: Actualiza un usuario existente si se proporciona un ID válido y las contraseñas coinciden.
 * - DELETE /users/{id}: Elimina un usuario por ID, devolviendo true/false según el resultado.
 * 
 * Mejoras pendientes:
 * - Usar ResponseEntity para devolver códigos HTTP claros en lugar de null.
 * - Mejorar manejo de validaciones para evitar NullPointerException en update.
 * - Corregir el mensaje de error de confirmación de contraseñas.
 */

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
        }// no se retornan contraseñas
        return usuariosEnviar;
    }

    // agregar un nuevo Usuario
    @PostMapping
    public UsuarioModel addUsuario(@RequestBody UsuarioRequest usuario) {
        try{
            if(usuario.getMail() == null || usuario.getPassword() == null || usuario.getRol() == null ||usuario.getConfirmPassword() == null || usuario.getId() != null){
                throw new IllegalArgumentException("El nombre, la contraseña y el rol son obligatorios");
            }
            if (usuario.getPassword().equals(usuario.getConfirmPassword())){
                UsuarioModel usuarioModel =new UsuarioModel();
                usuarioModel.setPassword(usuario.getPassword());
                usuarioModel.setMail(usuario.getMail());
                usuarioModel.setRol(usuario.getRol());
                
                return  usuarioGestion.addUsuario(usuarioModel); //devuelve el mismo usuario con el id asignado
            }else {
                throw new Exception("Error al confirmar contrasñas");
            }
        } catch (Exception e) {
            return null;
        }
    } 

    // actualizar un usuario implicitamente utiliza el id del usuario.
    @PutMapping
    public UsuarioModel updateUsuario(@RequestBody UsuarioRequest usuario ) {
        UsuarioModel usuarioModel =new UsuarioModel();
        if(usuario.getPassword().equals(usuario.getConfirmPassword()) && usuario.getId() != 0){
            usuarioModel.setId(usuario.getId());
            usuarioModel.setPassword(usuario.getPassword());
            usuarioModel.setMail(usuario.getMail());
            usuarioModel.setRol(usuario.getRol());
            
            usuarioGestion.updateUsuario(usuarioModel); //retorna el usuario  modificado
        }else {
            return null;
        }
        return usuarioModel;
        
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public boolean deleteUsuario(@PathVariable Long id) {
        boolean dato  =  usuarioGestion.deleteUsuario(id);
        return dato; // retorna true o false según se haya podido eliminar o no
    }


}