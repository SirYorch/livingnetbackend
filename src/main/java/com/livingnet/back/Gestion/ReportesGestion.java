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

// Clase de gestión, util en caso de escalar, para manejar lógica de negocio
// sirve principalmente para transicionar entre servicio persistencia, solo se comentan los métodos con lógica interna
@Service
public class ReportesGestion {

    private final ReportesDAO reportesDAO;

    @Autowired
    ReporteVacioDAO reporteVacioDAO;

    public ReportesGestion(ReportesDAO reportesDAO) {
        this.reportesDAO = reportesDAO;
    }

    public List<ReporteModel> getReportes() {
        return reportesDAO.findAll();
    }

     public ReporteModel addReporte(ReporteModel reporte) {
        return reportesDAO.addReporte(reporte);
    }

    //  devuelve un map de si se elimina el reporte, y la imagen que le corresponde, sirve para permitir que no se almacene informacion basura
    public Map<String,Boolean> deleteReporte(Long id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("reporte", reportesDAO.deleteImagen(id));
        response.put("imagen", reportesDAO.deleteReporte(id));
        
        return response;
    }

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

    public List<ReporteModel> getReportesFiltros(ReporteRequest rq) {
        return reportesDAO.getReportesFiltrado(rq);
    }

    public Long getCantidadReportes(ReporteRequest body) {
        return reportesDAO.getCantidadReportes(body);
    }


    // método para obtener un hashmap de los desplegables, sirve para poder autorellenar campos en el frontend, se devuelven los valores distintos de cada categoría.
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

    public boolean checkImageExist(String filePath) {        
        return reportesDAO.checkImageExist(filePath);
    }

    public boolean checkImage(String foto_url) {
        return reportesDAO.checkImage(foto_url);
    }

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
