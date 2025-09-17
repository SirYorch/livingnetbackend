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

    @PersistenceContext
    private EntityManager em;

    public Optional<UsuarioModel> buscarPorId(Long id) {
        UsuarioModel u = em.find(UsuarioModel.class, id);
        System.out.println("Usuario desde EntityManager: " + u);
        return Optional.ofNullable(u);
    }

    public UsuarioModel buscarPorEmailYPassword(String mail, String password) {
        try {
            System.out.println("Buscando usuario con mail: " + mail + " y password: " + password);
            
            return em.createQuery("SELECT u FROM UsuarioModel u WHERE u.mail = :mail AND u.password = :password", UsuarioModel.class)
                    .setParameter("mail", mail)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<UsuarioModel> getUsuarios() {
        return em.createQuery("SELECT u FROM UsuarioModel u", UsuarioModel.class)
                 .getResultList();
    }

    @Transactional
    public UsuarioModel save(UsuarioModel usuario) {
        System.out.println(usuario.getRol()+" "+usuario.getMail()+" "+usuario.getPassword()+" "+ usuario.getId());
        em.persist(usuario);
        return usuario;
    }

    @Transactional
    public UsuarioModel updateUsuario(UsuarioModel usuario) {
        return em.merge(usuario);
    }

    @Transactional
    public boolean deleteUsuario(Long id) {
         return em.createQuery("DELETE FROM UsuarioModel u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

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
}
