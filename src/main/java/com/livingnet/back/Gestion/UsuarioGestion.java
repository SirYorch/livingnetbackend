package com.livingnet.back.Gestion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.UsuarioDAO;
import com.livingnet.back.Model.UsuarioModel;


// clase de gestion de usuarios, maneja la logica de negocio
// la clase cuenta solo con un crud, y métodos e transición no se comentan métodos con lógica
@Service
public class UsuarioGestion {

    private final UsuarioDAO usuarioDAO;

    // Inyección de la implementación concreta del DAO
    public UsuarioGestion(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
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
