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

    public Long getCantidadReportes() {
        return reportesDAO.getCantidadReportes();
    }
}
