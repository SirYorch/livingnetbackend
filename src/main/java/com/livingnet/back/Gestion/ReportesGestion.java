package com.livingnet.back.Gestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.livingnet.back.DAO.ReportesDAO;
import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;

// Clase de gestión, util en caso de escalar, para manejar lógica de negocio
// sirve principalmente para transicionar entre servicio persistencia, solo se comentan los métodos con lógica interna
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

    //  devuelve un map de si se elimina el reporte, y la imagen que le corresponde, sirve para permitir que no se almacene informacion basura
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


}
