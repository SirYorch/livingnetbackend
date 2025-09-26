package com.livingnet.back.DAO;

import com.livingnet.back.Model.LocationRequest;
import com.livingnet.back.Model.ReporteVacioModel;
import com.livingnet.back.Model.UsuarioModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Date;

import org.springframework.stereotype.Repository;


/**
 * Clase de acceso a datos para reportes vacíos.
 * Maneja la persistencia de reportes vacíos en la base de datos.
 */
@Repository
public class ReporteVacioDAO{

    @PersistenceContext //entidad que permite realizar transacciones
    private EntityManager em;

    /**
     * Crea un nuevo reporte vacío para un usuario en una ubicación específica.
     * @param idUsuario El ID del usuario (no utilizado en la implementación actual).
     * @param lugar El objeto LocationRequest con la latitud y longitud.
     * @param usuario El objeto UsuarioModel del usuario.
     * @return El reporte vacío creado.
     */
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

    /**
     * Obtiene el reporte vacío asociado a un usuario.
     * @param usuario El ID del usuario.
     * @return El objeto ReporteVacioModel, o null si no se encuentra.
     */
    public ReporteVacioModel getReporteVacio(Long usuario) {
        try {
            return em.createQuery("SELECT r FROM ReporteVacioModel r WHERE r.usuario.id = :usuario", ReporteVacioModel.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Elimina el reporte vacío de un usuario.
     * @param usuario El ID del usuario.
     * @return true si el reporte fue eliminado, false en caso contrario.
     */
    @Transactional
    public boolean deleteReporteVacio(Long usuario) {
        return em.createQuery("DELETE FROM ReporteVacioModel r WHERE r.usuario.id = :usuario")
                .setParameter("usuario", usuario)
                .executeUpdate() > 0;
    }

    /**
     * Actualiza un reporte vacío existente.
     * @param rpm El objeto ReporteVacioModel con los datos actualizados.
     * @return El reporte vacío actualizado.
     */
    @Transactional
    public ReporteVacioModel actualizarReporteVacio(ReporteVacioModel rpm) {
        
        return em.merge(rpm);
    }
}
