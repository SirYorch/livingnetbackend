package com.livingnet.back.DAO;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    
    @PersistenceContext //entidad que permite realizar transacciones
    private EntityManager em;


    // metodo que devuelve todos los reportes en forma de lista
    public List<ReporteModel> findAll() {
         try {
            return em.createQuery("SELECT r FROM ReporteModel r", ReporteModel.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    // método que devuelve un reporte, sirve para generar reportes en la base de datos.
    @Transactional
    public ReporteModel addReporte(ReporteModel reporte) {
        em.persist(reporte);
        return reporte;
    }

    //método para eliminar reportes, devuelve un booleano si el reporte se elimina o no
    @Transactional
    public boolean deleteReporte(Long id) {
        
        return em.createQuery("DELETE FROM ReporteModel r WHERE r.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    //método para actualizar reporte realiza un merge en la base de datos, utiliza el id, aunque de forma implicita
    @Transactional
    public ReporteModel updateReporte(ReporteModel reporte) {

        
        return em.merge(reporte);
    }



    // método para eliminar imagenes, las imagenes que se encuentran en reportes utilizando el url?reportes, sirve para cuando se elimina un reporte, se elimine tambien su imagen.
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

    // Método auxiliar para construir el JPQL y setear parámetros
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

    // Método para contar reportes tomando en cuenta los filtros aplicados
    public Long getCantidadReportes(ReporteRequest request) {
        TypedQuery<Long> query = buildQuery(
            request,
            "SELECT COUNT(r) FROM ReporteModel r WHERE 1=1",
            Long.class
        );
        return query.getSingleResult();
    }

    // Método para obtener reportes filtrados, este utiliza paginación para noo enviar muchos datos por solicitud
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

    // método genérico para obtener valores distintos de un campo
    public List<String> getDistinctValues(String field) {
        String jpql = "SELECT DISTINCT r." + field + " FROM ReporteModel r WHERE r." + field + " IS NOT NULL";
        return em.createQuery(jpql, String.class).getResultList();
    }

    // método para saber si existe algún reporte con la imagen, sirve para que si se sube una imagen, pero no se crea un reporte, la imagen se elimine por si sola, evitando tener basura en el almacenamiento. se usa con triggers
    public boolean checkImageExist(String filePath) {
        Long count = em.createQuery(
            "SELECT COUNT(r) FROM ReporteModel r WHERE r.foto_url = :filePath", Long.class)
            .setParameter("filePath", filePath)
            .getSingleResult();
        return count > 0;
    }

    //metodo que devuelve un booleano de si la imagen existe o no
    public boolean checkImage(String path) {
        File file = new File(ImageProcessing.UPLOAD_DIR + path);
        return file.exists();
    }

    public ReporteModel getReporteById(long id) {
         try {
            return em.createQuery("SELECT r FROM ReporteModel r WHERE id = :id", ReporteModel.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
