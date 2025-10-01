package com.livingnet.back.DAO;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;
import com.livingnet.back.Servicios.ImageProcessing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * Clase de acceso a datos para reportes.
 * Maneja la persistencia de reportes en la base de datos utilizando JPA.
 */
@Repository
public class ReportesDAO {
    
    @PersistenceContext //entidad que permite realizar transacciones
    private EntityManager em;


    /**
     * Devuelve una lista con todos los reportes almacenados en la base de datos.
     * @return Lista de objetos ReporteModel, o null si ocurre un error.
     */
    public List<ReporteModel> findAll() {
         try {
            return em.createQuery("SELECT r FROM ReporteModel r", ReporteModel.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Agrega un nuevo reporte a la base de datos.
     * @param reporte El objeto ReporteModel a persistir.
     * @return El reporte persistido.
     */
    @Transactional
    public ReporteModel addReporte(ReporteModel reporte) {
        em.persist(reporte);
        return reporte;
    }

    /**
     * Elimina un reporte de la base de datos por su ID.
     * @param id El ID del reporte a eliminar.
     * @return true si el reporte fue eliminado, false en caso contrario.
     */
    @Transactional
    public boolean deleteReporte(Long id) {
        
        return em.createQuery("DELETE FROM ReporteModel r WHERE r.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    /**
     * Actualiza un reporte existente en la base de datos.
     * @param reporte El objeto ReporteModel con los datos actualizados.
     * @return El reporte actualizado.
     */
    @Transactional
    public ReporteModel updateReporte(ReporteModel reporte) {

        
        return em.merge(reporte);
    }



    /**
     * Elimina la imagen asociada a un reporte por su ID.
     * @param id El ID del reporte cuya imagen se desea eliminar.
     * @return true si la imagen fue eliminada, false en caso contrario.
     */
    public Boolean deleteImagen(Long id) {
        ReporteModel reporte = em.find(ReporteModel.class, id);
        if (reporte != null && reporte.getFoto_url() != null && !reporte.getFoto_url().isEmpty()) {
            ImageProcessing.deleteImagen(reporte.getFoto_url());
            return true;
        }
        return false;
    }

    /**
     * Método auxiliar para construir consultas JPQL con filtros y parámetros.
     * @param request El objeto ReporteRequest con los filtros.
     * @param baseQuery La consulta base JPQL.
     * @param resultClass La clase del resultado de la consulta.
     * @return La consulta TypedQuery configurada.
     */
    private <T> TypedQuery<T> buildQuery(ReporteRequest request, String baseQuery, Class<T> resultClass) {
        StringBuilder jpql = new StringBuilder(baseQuery);

        if (request.getFecha() != null) {
            jpql.append(" AND r.fecha BETWEEN :fechaInicio AND :fechaFin");
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

        TypedQuery<T> query = em.createQuery(jpql.toString(), resultClass);

        // Seteo de parámetros
        if (request.getFecha() != null) {
            LocalDate fechaLocal = request.getFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

            LocalDateTime inicio = fechaLocal.atStartOfDay();
            LocalDateTime fin = fechaLocal.plusDays(1).atStartOfDay().minusNanos(1);

            query.setParameter("fechaInicio", java.util.Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant()));
            query.setParameter("fechaFin", java.util.Date.from(fin.atZone(ZoneId.systemDefault()).toInstant()));

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

        return query;
    }

    /**
     * Cuenta el número de reportes que coinciden con los filtros aplicados.
     * @param request El objeto ReporteRequest con los filtros.
     * @return El número de reportes que coinciden.
     */
    public Long getCantidadReportes(ReporteRequest request) {
        TypedQuery<Long> query = buildQuery(
            request,
            "SELECT COUNT(r) FROM ReporteModel r WHERE 1=1",
            Long.class
        );
        return query.getSingleResult();
    }

    /**
     * Obtiene una lista de reportes filtrados con paginación.
     * @param request El objeto ReporteRequest con los filtros y parámetros de paginación.
     * @return Lista de reportes filtrados.
     */
    public List<ReporteModel> getReportesFiltrado(ReporteRequest request) {
        TypedQuery<ReporteModel> query = buildQuery(
            request,
            "SELECT r FROM ReporteModel r WHERE 1=1",
            ReporteModel.class
        );

        int datos = request.getDatos() > 0 ? request.getDatos() : 10; // default 10
        int pagina = request.getPagina() > 0 ? request.getPagina() : 1; // default 1
        int offset = (pagina - 1) * datos;

        query.setFirstResult(offset);
        query.setMaxResults(datos);

        return query.getResultList();
    }

    /**
     * Obtiene los valores distintos de un campo específico en los reportes.
     * @param field El nombre del campo.
     * @return Lista de valores distintos.
     */
    public List<String> getDistinctValues(String field) {
        String jpql = "SELECT DISTINCT r." + field + " FROM ReporteModel r WHERE r." + field + " IS NOT NULL";
        return em.createQuery(jpql, String.class).getResultList();
    }

    /**
     * Verifica si existe algún reporte asociado a una imagen específica.
     * @param filePath La ruta del archivo de la imagen.
     * @return true si existe al menos un reporte con esa imagen, false en caso contrario.
     */
    public boolean checkImageExist(String filePath) {
        Long count = em.createQuery(
            "SELECT COUNT(r) FROM ReporteModel r WHERE r.foto_url = :filePath", Long.class)
            .setParameter("filePath", filePath)
            .getSingleResult();
        return count > 0;
    }

    /**
     * Verifica si un archivo de imagen existe en el sistema de archivos.
     * @param path La ruta del archivo.
     * @return true si el archivo existe, false en caso contrario.
     */
    public boolean checkImage(String path) {
        File file = new File(ImageProcessing.UPLOAD_IMG + path);
        return file.exists();
    }

    /**
     * Obtiene un reporte por su ID.
     * @param id El ID del reporte.
     * @return Un Optional con el objeto ReporteModel correspondiente, vacío si no se encuentra.
     */
    public Optional<ReporteModel> getReporteById(long id) {
        try {
            ReporteModel reporte = em.createQuery("SELECT r FROM ReporteModel r WHERE id = :id", ReporteModel.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(reporte);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Elimina la firma asociada a un reporte por su ID.
     * @param id El ID del reporte cuya firma se desea eliminar.
     * @return true si la firma fue eliminada, false en caso contrario.
     */
    public Boolean deleteFirma(Long id) {
        ReporteModel reporte = em.find(ReporteModel.class, id);
        if (reporte != null && reporte.getFirmaUrl() != null && !reporte.getFirmaUrl().isEmpty()) {
            ImageProcessing.deleteSignature(reporte.getFirmaUrl());
            return true;
        }
        return false;
    }
}
