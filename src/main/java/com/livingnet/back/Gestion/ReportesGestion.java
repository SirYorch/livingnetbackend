package com.livingnet.back.Gestion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.ReporteVacioDAO;
import com.livingnet.back.DAO.ReportesDAO;
import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;
import com.livingnet.back.Model.ReporteVacioModel;

/**
 * Clase de gestión para reportes.
 * Maneja la lógica de negocio relacionada con reportes, actuando como intermediario entre los servicios y la capa de datos.
 */
@Service
public class ReportesGestion {

    private final ReportesDAO reportesDAO;

    @Autowired
    ReporteVacioDAO reporteVacioDAO;

    public ReportesGestion(ReportesDAO reportesDAO) {
        this.reportesDAO = reportesDAO;
    }

    /**
     * Obtiene una lista de todos los reportes.
     * @return Lista de ReporteModel.
     */
    public List<ReporteModel> getReportes() {
        return reportesDAO.findAll();
    }

    /**
     * Agrega un nuevo reporte.
     * @param reporte El ReporteModel a agregar.
     * @return El reporte agregado.
     */
     public ReporteModel addReporte(ReporteModel reporte) {
        return reportesDAO.addReporte(reporte);
    }

    /**
     * Elimina un reporte y su imagen asociada.
     * @param id El ID del reporte a eliminar.
     * @return Un mapa con el estado de eliminación del reporte y la imagen.
     */
    public Map<String,Boolean> deleteReporte(Long id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("reporte", reportesDAO.deleteImagen(id));
        response.put("imagen", reportesDAO.deleteReporte(id));
        
        return response;
    }

    /**
     * Actualiza un reporte existente con los nuevos datos proporcionados.
     * @param reporte El ReporteModel con los datos actualizados.
     * @return El reporte actualizado.
     */
    public ReporteModel updateReporte(ReporteModel reporte) {
        // get reporte by id
        ReporteModel existingReporte = reportesDAO.getReporteById(reporte.getId());
        existingReporte.setActividad(reporte.getActividad());
        existingReporte.setAgencia(reporte.getAgencia());
        existingReporte.setClima(reporte.getClima());
        existingReporte.setComplejidad_actividad(reporte.getComplejidad_actividad());
        existingReporte.setCuadrilla(reporte.getCuadrilla());
        existingReporte.setEstado_actividad(reporte.getEstado_actividad());
        existingReporte.setFormato_actividad(reporte.getFormato_actividad());
        existingReporte.setFoto_url(reporte.getFoto_url());
        existingReporte.setMotivo_retraso(reporte.getMotivo_retraso());
        existingReporte.setObservaciones(reporte.getObservaciones());
        existingReporte.setTipo_actividad(reporte.getTipo_actividad());
        existingReporte.setJefe_cuadrilla(reporte.getJefe_cuadrilla());
        existingReporte.setAyudante_tecnico(reporte.getAyudante_tecnico());
        existingReporte.setKilometraje_inicio(reporte.getKilometraje_inicio());
        existingReporte.setKilometraje_fin(reporte.getKilometraje_fin());
        existingReporte.setRouter(reporte.getRouter());
        existingReporte.setOnu(reporte.getOnu());
        existingReporte.setDrop(reporte.getDrop());
        existingReporte.setRoseta(reporte.getRoseta());
        existingReporte.setTensores(reporte.getTensores());
        existingReporte.setConectores(reporte.getConectores());
        existingReporte.setCamara(reporte.getCamara());





        return reportesDAO.updateReporte(reporte);    
    }

    /**
     * Obtiene una lista de reportes filtrados según los criterios especificados.
     * @param rq El ReporteRequest con los filtros.
     * @return Lista de reportes filtrados.
     */
    public List<ReporteModel> getReportesFiltros(ReporteRequest rq) {
        return reportesDAO.getReportesFiltrado(rq);
    }

    /**
     * Obtiene la cantidad de reportes que coinciden con los filtros aplicados.
     * @param body El ReporteRequest con los filtros.
     * @return La cantidad de reportes.
     */
    public Long getCantidadReportes(ReporteRequest body) {
        return reportesDAO.getCantidadReportes(body);
    }


    /**
     * Obtiene un mapa con los valores distintos para los campos desplegables.
     * @return Mapa con listas de valores únicos para cada categoría.
     */
    public Map<String, List<String>> getDesplegables() {
        Map<String, List<String>> desplegables = new HashMap<>();

        desplegables.put("agencia", reportesDAO.getDistinctValues("agencia"));
        desplegables.put("tipo_actividad", reportesDAO.getDistinctValues("tipo_actividad"));
        desplegables.put("formato_actividad", reportesDAO.getDistinctValues("formato_actividad"));
        desplegables.put("complejidad", reportesDAO.getDistinctValues("complejidad_actividad"));
        desplegables.put("estado", reportesDAO.getDistinctValues("estado_actividad"));
        desplegables.put("jefe_cuadrilla", reportesDAO.getDistinctValues("jefe_cuadrilla"));
        desplegables.put("cuadrilla", reportesDAO.getDistinctValues("cuadrilla"));
        desplegables.put("ayudante_tecnico", reportesDAO.getDistinctValues("ayudante_tecnico"));

        return desplegables;
    }

    /**
     * Verifica si existe un reporte asociado a la imagen especificada.
     * @param filePath La ruta del archivo de la imagen.
     * @return true si existe, false en caso contrario.
     */
    public boolean checkImageExist(String filePath) {
        return reportesDAO.checkImageExist(filePath);
    }

    /**
     * Verifica si la imagen existe en el sistema de archivos.
     * @param foto_url La URL de la foto.
     * @return true si existe, false en caso contrario.
     */
    public boolean checkImage(String foto_url) {
        return reportesDAO.checkImage(foto_url);
    }

    /**
     * Genera un reporte completo a partir de un reporte vacío y los datos proporcionados.
     * @param rpm El ReporteVacioModel con datos adicionales.
     * @param idUsuario El ID del usuario.
     * @param reporte El ReporteModel base.
     * @return El reporte generado y guardado.
     */
    public ReporteModel generarReporte(ReporteVacioModel rpm, Long idUsuario, ReporteModel reporte) {

        ReporteVacioModel rp = reporteVacioDAO.getReporteVacio(idUsuario);

        reporte.setHorainicio(rp.getHorainicio());
        reporte.setFecha(rp.getFecha());
        reporte.setHorafin(new Date()); // hora a la que se crea

        reporte.setLatitudInicio(rp.getLatitudInicio());
        reporte.setLongitudInicio(rp.getLongitudInicio());
        reporte.setLatitudFin(rpm.getLatitudFin());
        reporte.setLongitudFin(rpm.getLongitudFin());

        //obtener los valores de reporte
        reporte.setAgencia(rpm.getAgencia());
        reporte.setActividad(rpm.getActividad());
        reporte.setCuadrilla(rpm.getCuadrilla());
        reporte.setJefe_cuadrilla(rpm.getJefe_cuadrilla());
        reporte.setTipo_actividad(rpm.getTipo_actividad());
        reporte.setFormato_actividad(rpm.getFormato_actividad());
        reporte.setComplejidad_actividad(rpm.getComplejidad_actividad());
        reporte.setEstado_actividad(rpm.getEstado_actividad());
        reporte.setClima(rpm.getClima());

        // Strings opcionales
        reporte.setAyudante_tecnico(rpm.getAyudante_tecnico());
        reporte.setMotivo_retraso(rpm.getMotivo_retraso());
        reporte.setObservaciones(rpm.getObservaciones());

        // Doubles
        reporte.setKilometraje_inicio(rpm.getKilometraje_inicio());
        reporte.setKilometraje_fin(rpm.getKilometraje_fin());

        // Ints
        reporte.setRouter(rpm.getRouter());
        reporte.setOnu(rpm.getOnu());
        reporte.setDrop(rpm.getDrop());
        reporte.setRoseta(rpm.getRoseta());
        reporte.setTensores(rpm.getTensores());
        reporte.setConectores(rpm.getConectores());
        reporte.setCamara(rpm.getCamara());


        //eliminamos el reportevacío del usuario 
        reporteVacioDAO.deleteReporteVacio(idUsuario);
        return reportesDAO.addReporte(reporte);

        
    }


}
