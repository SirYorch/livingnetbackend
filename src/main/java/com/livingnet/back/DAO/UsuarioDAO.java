package com.livingnet.back.DAO;

import com.livingnet.back.Model.UsuarioModel;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//clase de acceso a datos de usuarios, maneja la persistencia
/**
 * Clase de acceso a datos para usuarios.
 * Maneja la persistencia de usuarios en la base de datos utilizando JPA.
 */
@Repository
public class UsuarioDAO {

    /**
     * EntityManager para realizar operaciones de persistencia.
     */
    @PersistenceContext // entidad para realizar transacciones jpa
    private EntityManager em;


    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un Optional con el UsuarioModel si se encuentra, vacío en caso contrario.
     */
    public Optional<UsuarioModel> buscarPorId(Long id) {
        UsuarioModel u = em.find(UsuarioModel.class, id);
        return Optional.ofNullable(u);
    }

    /**
     * Busca un usuario por email y contraseña para validación de credenciales.
     * @param mail El email del usuario.
     * @param password La contraseña del usuario.
     * @return El UsuarioModel si las credenciales son correctas, null en caso contrario.
     */
    public UsuarioModel buscarPorEmailYPassword(String mail, String password) {
        try {
            return em.createQuery("SELECT u FROM UsuarioModel u WHERE u.mail = :mail AND u.password = :password", UsuarioModel.class)
                    .setParameter("mail", mail)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtiene una lista de todos los usuarios registrados.
     * @return Lista de UsuarioModel.
     */
    public List<UsuarioModel> getUsuarios() {
        return em.createQuery("SELECT u FROM UsuarioModel u", UsuarioModel.class)
                 .getResultList();
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param usuario El UsuarioModel a guardar.
     * @return El usuario guardado.
     */
    @Transactional
    public UsuarioModel save(UsuarioModel usuario) {
        System.out.println(usuario.getRol()+" "+usuario.getMail()+" "+usuario.getPassword()+" "+ usuario.getId());
        em.persist(usuario);
        return usuario;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * @param usuario El UsuarioModel con los datos actualizados.
     * @return El usuario actualizado.
     */
    @Transactional
    public UsuarioModel updateUsuario(UsuarioModel usuario) {
        return em.merge(usuario);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario fue eliminado, false en caso contrario.
     */
    @Transactional
    public boolean deleteUsuario(Long id) {
         return em.createQuery("DELETE FROM UsuarioModel u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    /**
     * Obtiene un usuario por su email.
     * @param mail El email del usuario.
     * @return El UsuarioModel correspondiente, o null si no se encuentra.
     */
    public UsuarioModel getUsuarioPorMail(String mail) {
        try {
            return em.createQuery("SELECT u FROM UsuarioModel u WHERE u.mail = :mail", UsuarioModel.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException nre) {
            System.out.println("NoResultException -> no existe usuario con mail=" + mail);
            return null;
        } catch (jakarta.persistence.NonUniqueResultException nue) {
            System.out.println("NonUniqueResultException -> hay >1 usuario con mail=" + mail);
            nue.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id El ID del usuario.
     * @return El UsuarioModel correspondiente, o null si no se encuentra.
     */
    public UsuarioModel getUsuarioPorId(Long id) {
        try {
            return em.createQuery("SELECT u FROM UsuarioModel u WHERE u.id = :id", UsuarioModel.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
