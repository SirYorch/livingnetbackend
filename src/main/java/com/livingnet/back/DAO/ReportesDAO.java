package com.livingnet.back.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;
import com.livingnet.back.Servicios.ImageProcessing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

// clase de acceso a datos, maneja la persistencia de reportes
@Repository
public class ReportesDAO {
    
    @PersistenceContext
    private EntityManager em;

    public List<ReporteModel> findAll() {
         try {
            return em.createQuery("SELECT r FROM ReporteModel r", ReporteModel.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ReporteModel addReporte(ReporteModel reporte) {
        em.persist(reporte);
        return reporte;
    }

    @Transactional
    public boolean deleteReporte(Long id) {
        System.out.println("Se llega aquí");
        return em.createQuery("DELETE FROM ReporteModel r WHERE r.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public ReporteModel updateReporte(ReporteModel reporte) {
        System.out.println("Se llega aquí");
        return em.merge(reporte);
    }

    public Long getCantidadReportes(ReporteRequest request) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(r) FROM ReporteModel r WHERE 1=1");

        
        if (request.getFecha() != null) {
            jpql.append(" AND r.fecha = :fecha");
        }
        if (request.getAgencia() != null && !request.getAgencia().isEmpty()) {
            jpql.append(" AND r.agencia = :agencia");
        }
        if (request.getTipo_actividad() != null && !request.getTipo_actividad().isEmpty()) {
            jpql.append(" AND r.tipo_actividad = :tipo_actividad");
        }
        if (request.getFormato_actividad() != null && !request.getFormato_actividad().isEmpty()) {
            jpql.append(" AND r.formato_actividad = :formato_actividad");
        }
        if (request.getCuadrilla() != null && !request.getCuadrilla().isEmpty()) {
            jpql.append(" AND r.cuadrilla = :cuadrilla");
        }
        if (request.getComplejidad_actividad() != null && !request.getComplejidad_actividad().isEmpty()) {
            jpql.append(" AND r.complejidad_actividad = :complejidad_actividad");
        }
        if (request.getEstado_actividad() != null && !request.getEstado_actividad().isEmpty()) {
            jpql.append(" AND r.estado_actividad = :estado_actividad");
        }
        if (request.getJefe_cuadrilla() != null && !request.getJefe_cuadrilla().isEmpty()) {
            jpql.append(" AND r.jefe_cuadrilla = :jefe_cuadrilla");
        }
        if (request.getAyudante_tecnico() != null && !request.getAyudante_tecnico().isEmpty()) {
            jpql.append(" AND r.ayudante_tecnico = :ayudante_tecnico");
        }

                if (request.getRetraso() != null) {
            if (request.getRetraso().equals("yes")) {
                jpql.append(" AND r.motivo_retraso IS NOT NULL");
            } else if (request.getRetraso().equals("no")) {
                jpql.append(" AND r.motivo_retraso IS NULL");
            }
        }

        
        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

        // Setear parámetros
        if (request.getFecha() != null) {
            query.setParameter("fecha", request.getFecha());
        }
        if (request.getAgencia() != null && !request.getAgencia().isEmpty()) {
            query.setParameter("agencia", request.getAgencia());
        }
        if (request.getTipo_actividad() != null && !request.getTipo_actividad().isEmpty()) {
            query.setParameter("tipo_actividad", request.getTipo_actividad());
        }
        if (request.getFormato_actividad() != null && !request.getFormato_actividad().isEmpty()) {
            query.setParameter("formato_actividad", request.getFormato_actividad());
        }
        if (request.getComplejidad_actividad() != null && !request.getComplejidad_actividad().isEmpty()) {
            query.setParameter("complejidad_actividad", request.getComplejidad_actividad());
        }
        if (request.getEstado_actividad() != null && !request.getEstado_actividad().isEmpty()) {
            query.setParameter("estado_actividad", request.getEstado_actividad());
        }
        if (request.getCuadrilla() != null && !request.getCuadrilla().isEmpty()) {
            query.setParameter("cuadrilla", request.getCuadrilla());
        }
        if (request.getJefe_cuadrilla() != null && !request.getJefe_cuadrilla().isEmpty()) {
            query.setParameter("jefe_cuadrilla", request.getJefe_cuadrilla());
        }
        if (request.getAyudante_tecnico() != null && !request.getAyudante_tecnico().isEmpty()) {
            query.setParameter("ayudante_tecnico", request.getAyudante_tecnico());
        }



        return query.getSingleResult();
    }

    
    public List<ReporteModel> getReportesFiltrado(ReporteRequest request) {
        StringBuilder jpql = new StringBuilder("SELECT r FROM ReporteModel r WHERE 1=1");

        if (request.getFecha() != null) {
            jpql.append(" AND r.fecha = :fecha");
        }
        if (request.getAgencia() != null && !request.getAgencia().isEmpty()) {
            jpql.append(" AND r.agencia = :agencia");
        }
        if (request.getTipo_actividad() != null && !request.getTipo_actividad().isEmpty()) {
            jpql.append(" AND r.tipo_actividad = :tipo_actividad");
        }
        if (request.getFormato_actividad() != null && !request.getFormato_actividad().isEmpty()) {
            jpql.append(" AND r.formato_actividad = :formato_actividad");
        }
        if (request.getCuadrilla() != null && !request.getCuadrilla().isEmpty()) {
            jpql.append(" AND r.cuadrilla = :cuadrilla");
        }
        if (request.getComplejidad_actividad() != null && !request.getComplejidad_actividad().isEmpty()) {
            jpql.append(" AND r.complejidad_actividad = :complejidad_actividad");
        }
        if (request.getEstado_actividad() != null && !request.getEstado_actividad().isEmpty()) {
            jpql.append(" AND r.estado_actividad = :estado_actividad");
        }
        if (request.getJefe_cuadrilla() != null && !request.getJefe_cuadrilla().isEmpty()) {
            jpql.append(" AND r.jefe_cuadrilla = :jefe_cuadrilla");
        }
        if (request.getAyudante_tecnico() != null && !request.getAyudante_tecnico().isEmpty()) {
            jpql.append(" AND r.ayudante_tecnico = :ayudante_tecnico");
        }

        if (request.getRetraso() != null) {
            if (request.getRetraso().equals("yes")) {
                jpql.append(" AND r.motivo_retraso IS NOT NULL");
            } else if (request.getRetraso().equals("no")) {
                jpql.append(" AND r.motivo_retraso IS NULL");
            }
        }

        // Crear query
        TypedQuery<ReporteModel> query = em.createQuery(jpql.toString(), ReporteModel.class);

        // Setear parámetros
        if (request.getFecha() != null) {
            query.setParameter("fecha", request.getFecha());
        }
        if (request.getAgencia() != null && !request.getAgencia().isEmpty()) {
            query.setParameter("agencia", request.getAgencia());
        }
        if (request.getTipo_actividad() != null && !request.getTipo_actividad().isEmpty()) {
            query.setParameter("tipo_actividad", request.getTipo_actividad());
        }
        if (request.getFormato_actividad() != null && !request.getFormato_actividad().isEmpty()) {
            query.setParameter("formato_actividad", request.getFormato_actividad());
        }
        if (request.getComplejidad_actividad() != null && !request.getComplejidad_actividad().isEmpty()) {
            query.setParameter("complejidad_actividad", request.getComplejidad_actividad());
        }
        if (request.getEstado_actividad() != null && !request.getEstado_actividad().isEmpty()) {
            query.setParameter("estado_actividad", request.getEstado_actividad());
        }
        if (request.getCuadrilla() != null && !request.getCuadrilla().isEmpty()) {
            query.setParameter("cuadrilla", request.getCuadrilla());
        }
        if (request.getJefe_cuadrilla() != null && !request.getJefe_cuadrilla().isEmpty()) {
            query.setParameter("jefe_cuadrilla", request.getJefe_cuadrilla());
        }
        if (request.getAyudante_tecnico() != null && !request.getAyudante_tecnico().isEmpty()) {
            query.setParameter("ayudante_tecnico", request.getAyudante_tecnico());
        }

        // Paginación
        int datos = request.getDatos() > 0 ? request.getDatos() : 10; // default 10
        int pagina = request.getPagina() > 0 ? request.getPagina() : 1; // default página 1
        int offset = (pagina - 1) * datos;

        query.setFirstResult(offset); // desde qué registro empieza
        query.setMaxResults(datos);   // cuántos registros devuelve

        return query.getResultList();
    }

    public Boolean deleteImagen(Long id) {
        ReporteModel reporte = em.find(ReporteModel.class, id);
        if (reporte != null && reporte.getFoto_url() != null && !reporte.getFoto_url().isEmpty()) {
            java.io.File file = new java.io.File(ImageProcessing.UPLOAD_DIR + reporte.getFoto_url());
            if (file.exists()) {
                file.delete();
                return true;
            }
        }
        return false;
    }

    public List<String> getAgencias() {
         return em.createQuery(
            "SELECT DISTINCT r.agencia FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getTiposActividad() {
         return em.createQuery(
            "SELECT DISTINCT r.tipo_actividad FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getComplejidades() {
         return em.createQuery(
            "SELECT DISTINCT r.complejidad_actividad FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getEstados() {
         return em.createQuery(
            "SELECT DISTINCT r.estado_actividad FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getJefesCuadrilla() {
         return em.createQuery(
            "SELECT DISTINCT r.jefe_cuadrilla FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getCuadrillas() {
         return em.createQuery(
            "SELECT DISTINCT r.cuadrilla FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getAyudantesTecnico() {
         return em.createQuery(
            "SELECT DISTINCT r.ayudante_tecnico FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public List<String> getFormatosActividad() {
        return em.createQuery(
            "SELECT DISTINCT r.formato_actividad FROM ReporteModel r", String.class
        )
        .getResultList();
    }

    public boolean checkImageExist(String filePath) {
    try {
        int count = em.createQuery(
                "SELECT COUNT(r) FROM ReporteModel r WHERE r.foto_url = :filePath", Integer.class)
                .setParameter("filePath", filePath)
                .getSingleResult();
        return count>0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
