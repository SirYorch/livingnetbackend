package com.livingnet.back.Gestion;

import java.util.List;
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

    public boolean deleteReporte(Long id) {
        return reportesDAO.deleteReporte(id);
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
