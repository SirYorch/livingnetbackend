package com.livingnet.back.Gestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.ReportesDAO;
import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;

// Clase de gestión, util en caso de escalar, para manejar lógica de negocio
@Service
public class ReportesGestion {

    private final ReportesDAO reportesDAO;

    public ReportesGestion(ReportesDAO reportesDAO) {
        this.reportesDAO = reportesDAO;
    }

    public List<ReporteModel> getReportes() {
        return reportesDAO.findAll();
    }

     public ReporteModel addReporte(ReporteModel reporte) {
        return reportesDAO.addReporte(reporte);
    }

    public Map<String,Boolean> deleteReporte(Long id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("reporte", reportesDAO.deleteImagen(id));
        response.put("imagen", reportesDAO.deleteReporte(id));
        
        return response;
    }

    public ReporteModel updateReporte(ReporteModel reporte) {
        return reportesDAO.updateReporte(reporte);    
    }

    public List<ReporteModel> getReportesFiltros(ReporteRequest rq) {
        return reportesDAO.getReportesFiltrado(rq);
    }

    public Long getCantidadReportes(ReporteRequest body) {
        return reportesDAO.getCantidadReportes(body);
    }

    public Map<String, List<String>> getDesplegables() {
        Map<String, List<String>> desplegables = new HashMap<>();
        // desplegables.put("fecha", reportesDAO.getFechas());
        desplegables.put("agencia", reportesDAO.getAgencias());
        desplegables.put("tipo_actividad", reportesDAO.getTiposActividad());
        desplegables.put("formato_actividad", reportesDAO.getFormatosActividad());
        desplegables.put("complejidad", reportesDAO.getComplejidades());
        desplegables.put("estado", reportesDAO.getEstados());
        desplegables.put("jefe_cuadrilla", reportesDAO.getJefesCuadrilla());
        desplegables.put("cuadrilla", reportesDAO.getCuadrillas());
        desplegables.put("ayudante_tecnico", reportesDAO.getAyudantesTecnico());

        return desplegables;
        
    }

    public boolean checkImageExist(String filePath) {

        
        return reportesDAO.checkImageExist(filePath);
    }
}
