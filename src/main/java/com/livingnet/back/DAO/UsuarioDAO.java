package com.livingnet.back.DAO;

import com.livingnet.back.Model.UsuarioModel;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//clase de acceso a datos de usuarios, maneja la persistencia
@Repository
public class UsuarioDAO {

    @PersistenceContext // entidad para realizar transacciones jpa
    private EntityManager em;


    //metodo para buscar usuarios por el id, 
    public Optional<UsuarioModel> buscarPorId(Long id) {
        UsuarioModel u = em.find(UsuarioModel.class, id);
        return Optional.ofNullable(u);
    }

    //metodos para buscar usuarios por email y password, sirve para las validación de credenciales en el inicio de sesión
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

    //metodo listar usuarios, sirve para listar todos los usuarios y que se conozcan usuarios y permisos de cada uno.
    public List<UsuarioModel> getUsuarios() {
        return em.createQuery("SELECT u FROM UsuarioModel u", UsuarioModel.class)
                 .getResultList();
    }

    // metodo para guardar usuarios, utilizando jpa.
    @Transactional
    public UsuarioModel save(UsuarioModel usuario) {
        System.out.println(usuario.getRol()+" "+usuario.getMail()+" "+usuario.getPassword()+" "+ usuario.getId());
        em.persist(usuario);
        return usuario;
    }

    // método para actualizar usuario, es utiliza la id de forma implicita.
    @Transactional
    public UsuarioModel updateUsuario(UsuarioModel usuario) {
        return em.merge(usuario);
    }

    // método para eliminar usuarios, utilizando el id, no se permite que un usuario se elimine a si mismo, revisar jwtFilter
    @Transactional
    public boolean deleteUsuario(Long id) {
         return em.createQuery("DELETE FROM UsuarioModel u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    //metodo get usuario por MAIl sirve para obtener el usuario utilizando solo el mail, sirve para la validacion en jwtFilter
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
