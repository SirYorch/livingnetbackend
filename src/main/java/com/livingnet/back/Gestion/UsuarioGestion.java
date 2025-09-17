package com.livingnet.back.Gestion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.UsuarioDAO;
import com.livingnet.back.Model.UsuarioModel;


// clase de gestion de usuarios, maneja la logica de negocio
@Service
public class UsuarioGestion {

    private final UsuarioDAO usuarioDAO;

    // Inyección de la implementación concreta del DAO
    public UsuarioGestion(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        Optional<UsuarioModel> usuario = usuarioDAO.buscarPorId(id);
        System.out.println("Usuario encontrado: " + usuario.orElse(null));
        return usuario;
    }

    public UsuarioModel buscarPorEmailYPassword(String email, String password) {
        return usuarioDAO.buscarPorEmailYPassword(email, password);
        
    }

    public List<UsuarioModel> getUsuarios() {
        return usuarioDAO.getUsuarios();
    }

    public UsuarioModel addUsuario(UsuarioModel usuario) {
        return usuarioDAO.save(usuario);
    }

    public UsuarioModel updateUsuario(UsuarioModel usuario) {
        return usuarioDAO.updateUsuario(usuario);
    }

    public boolean deleteUsuario(Long id) {
        return usuarioDAO.deleteUsuario(id);
    }

    public UsuarioModel getUsuarioPorMail(String mail) {
        return usuarioDAO.getUsuarioPorMail(mail);
    }
}
