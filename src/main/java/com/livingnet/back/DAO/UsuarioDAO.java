package com.livingnet.back.DAO;

import com.livingnet.back.Model.UsuarioModel;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    public UsuarioModel buscarPorEmailYPassword(String name, String password) {
        try {
            System.out.println("Buscando usuario con name: " + name + " y password: " + password);
            
            return em.createQuery("SELECT u FROM UsuarioModel u WHERE u.name = :name AND u.password = :password", UsuarioModel.class)
                    .setParameter("name", name)
                    .setParameter("password", password)
                    .getSingleResult();
                
        } catch (Exception e) {
            return null;
        }
    }

    public void findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
