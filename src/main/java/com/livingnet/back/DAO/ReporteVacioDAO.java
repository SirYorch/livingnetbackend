package com.livingnet.back.DAO;

import com.livingnet.back.Model.LocationRequest;
import com.livingnet.back.Model.ReporteVacioModel;
import com.livingnet.back.Model.UsuarioModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Date;

import org.springframework.stereotype.Repository;


@Repository
public class ReporteVacioDAO{

    @PersistenceContext //entidad que permite realizar transacciones
    private EntityManager em;

    @Transactional
    public ReporteVacioModel createReporte(Long idUsuario, LocationRequest lugar, UsuarioModel usuario){
        ReporteVacioModel rvm = new ReporteVacioModel();
        rvm.setLatitudInicio(lugar.getLatitud());
        rvm.setLongitudInicio(lugar.getLongitud());
        rvm.setUsuario(usuario);
        rvm.setHorainicio(new Date());
        rvm.setFecha(new Date());
        rvm.setRouter(0);
        rvm.setOnu(0);
        rvm.setRoseta(0);
        rvm.setDrop(0);
        rvm.setTensores(0);
        rvm.setConectores(0);
        rvm.setCamara(0);
        em.persist(rvm);
        return rvm;
    }

    public ReporteVacioModel getReporteVacio(Long usuario) {
        try {
            return em.createQuery("SELECT r FROM ReporteVacioModel r WHERE r.usuario.id = :usuario", ReporteVacioModel.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    @Transactional
    public boolean deleteReporteVacio(Long usuario) {
        return em.createQuery("DELETE FROM ReporteVacioModel r WHERE r.usuario.id = :usuario")
                .setParameter("usuario", usuario)
                .executeUpdate() > 0;
    }

    @Transactional
    public ReporteVacioModel actualizarReporteVacio(ReporteVacioModel rpm) {
        
        return em.merge(rpm);
    }
}
