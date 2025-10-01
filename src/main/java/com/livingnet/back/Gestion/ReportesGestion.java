package com.livingnet.back.Gestion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

    /**
     * Constructor de ReportesGestion.
     * Inyecta la dependencia de ReportesDAO.
     * @param reportesDAO El DAO para operaciones de reportes.
     */
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
     * @return Un mapa con el estado de eliminación del reporte, imagen y firma.
     */
    public Map<String,Boolean> deleteReporte(Long id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("firma", reportesDAO.deleteFirma(id));
        response.put("imagen", reportesDAO.deleteImagen(id));
        response.put("reporte", reportesDAO.deleteReporte(id));
        
        

        return response;
    }

    /**
     * Actualiza un reporte existente con los nuevos datos proporcionados.
     * @param reporte El ReporteModel con los datos actualizados.
     * @return El reporte actualizado, o null si la validación falla.
     */
    public ReporteModel updateReporte(ReporteModel reporte) {
        if (reporte.getActividad() == null ||
            reporte.getAgencia() == null ||
            reporte.getClima() == null ||
            reporte.getComplejidad_actividad() == null ||
            reporte.getCuadrilla() == null ||
            reporte.getEstado_actividad() == null ||
            reporte.getFormato_actividad() == null ||
            reporte.getJefe_cuadrilla() == null ||
            reporte.getTipo_actividad() == null ||
            reporte.getCamara() < 0 ||
            reporte.getConectores() < 0 ||
            reporte.getDrop() < 0 ||
            reporte.getKilometraje_fin() < 0 ||
            reporte.getKilometraje_inicio() < 0 ||
            reporte.getOnu() < 0 ||
            reporte.getRoseta() < 0 ||
            reporte.getRouter() < 0 ||
            reporte.getTensores() < 0 ||
            reporte.getId() == 0) {
            return null;
        }

        // get reporte by id
        Optional<ReporteModel> existingReporteOpt = reportesDAO.getReporteById(reporte.getId());
        if (existingReporteOpt.isEmpty()) {
            return null;
        }
        ReporteModel existingReporte = existingReporteOpt.get();

        // Excluir campos sensibles para evitar manipulación de datos: fechas, horas y coordenadas
        BeanUtils.copyProperties(reporte, existingReporte, "id", "fecha", "horainicio", "horafin", "latitudInicio", "longitudInicio", "latitudFin", "longitudFin");

        return reportesDAO.updateReporte(existingReporte);
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

        ///// datos entrantes

        reporte.setAgencia(rpm.getAgencia());
        reporte.setActividad(rpm.getActividad());
        reporte.setNombreCliente(rpm.getNombreCliente());
        reporte.setCedulaCliente(rpm.getCedulaCliente());
        reporte.setNumeroContrato(rpm.getNumeroContrato());
        reporte.setTelefonos(rpm.getTelefonos());
        reporte.setCorreo(rpm.getCorreo());
        reporte.setPlan(rpm.getPlan());
        reporte.setCoordenadas(rpm.getCoordenadas());
        reporte.setValorCobrar(rpm.getValorCobrar());
        reporte.setTipo_actividad(rpm.getTipo_actividad());
        reporte.setFormato_actividad(rpm.getFormato_actividad());
        reporte.setComplejidad_actividad(rpm.getComplejidad_actividad());
        reporte.setEstado_actividad(rpm.getEstado_actividad());
        reporte.setClima(rpm.getClima());
        reporte.setCuadrilla(rpm.getCuadrilla());
        reporte.setJefe_cuadrilla(rpm.getJefe_cuadrilla());
        reporte.setAyudante_tecnico(rpm.getAyudante_tecnico());
        reporte.setMotivo_retraso(rpm.getMotivo_retraso());
        reporte.setObservaciones(rpm.getObservaciones());
        reporte.setKilometraje_inicio(rpm.getKilometraje_inicio());
        reporte.setKilometraje_fin(rpm.getKilometraje_fin());
        reporte.setRouter(rpm.getRouter());
        reporte.setOnu(rpm.getOnu());
        reporte.setDrop(rpm.getDrop());
        reporte.setRoseta(rpm.getRoseta());
        reporte.setTensores(rpm.getTensores());
        reporte.setConectores(rpm.getConectores());
        reporte.setCamara(rpm.getCamara());
        

        //eliminamos el reportevacío del usuario
        ReporteModel rm = reportesDAO.addReporte(reporte);
        reporteVacioDAO.deleteReporteVacio(idUsuario);
        
        return rm;


    }

    /**
     * Obtiene una lista de todos los reportes vacíos.
     * @return Lista de ReporteVacioModel.
     */
    public List<ReporteVacioModel> getReportesVacios() {
        return reporteVacioDAO.findAll();
    }


}
