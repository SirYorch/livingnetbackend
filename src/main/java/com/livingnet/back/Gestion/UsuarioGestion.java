package com.livingnet.back.Gestion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.UsuarioDAO;
import com.livingnet.back.Model.UsuarioModel;


/**
 * Clase de gestión para usuarios.
 * Maneja la lógica de negocio relacionada con usuarios, proporcionando operaciones CRUD.
 */
@Service
public class UsuarioGestion {

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor de UsuarioGestion.
     * Inyecta la dependencia de UsuarioDAO.
     * @param usuarioDAO El DAO para operaciones de usuarios.
     */
    public UsuarioGestion(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un Optional con el UsuarioModel si se encuentra.
     */
    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    /**
     * Busca un usuario por email y contraseña para autenticación.
     * @param email El email del usuario.
     * @param password La contraseña del usuario.
     * @return El UsuarioModel si las credenciales son válidas, null en caso contrario.
     */
    public Optional<UsuarioModel> buscarPorEmailYPassword(String email, String password) {
        return usuarioDAO.buscarPorEmailYPassword(email, password);
        
    }

    /**
     * Obtiene una lista de todos los usuarios.
     * @return Lista de UsuarioModel.
     */
    public List<UsuarioModel> getUsuarios() {
        return usuarioDAO.getUsuarios();
    }

    /**
     * Agrega un nuevo usuario.
     * @param usuario El UsuarioModel a agregar.
     * @return El usuario agregado.
     */
    public UsuarioModel addUsuario(UsuarioModel usuario) {
        return usuarioDAO.save(usuario);
    }

    /**
     * Actualiza un usuario existente.
     * @param usuario El UsuarioModel con los datos actualizados.
     * @return El usuario actualizado.
     */
    public UsuarioModel updateUsuario(UsuarioModel usuario) {
        return usuarioDAO.updateUsuario(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario fue eliminado, false en caso contrario.
     */
    public boolean deleteUsuario(Long id) {
        return usuarioDAO.deleteUsuario(id);
    }

    /**
     * Obtiene un usuario por su email.
     * @param mail El email del usuario.
     * @return El UsuarioModel correspondiente, o null si no se encuentra.
     */
    public Optional<UsuarioModel> getUsuarioPorMail(String mail) {
        return usuarioDAO.getUsuarioPorMail(mail);
    }
}
