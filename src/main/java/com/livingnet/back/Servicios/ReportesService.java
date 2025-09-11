package com.livingnet.back.Servicios;

import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Gestion.ReportesGestion;

import java.util.List;



@RestController
@RequestMapping("/reports")


//clase de servicio, maneja la recepción y envío de datos mediante solicitudes de usuario
public class ReportesService {

    private final ReportesGestion reportesGestion;

    public ReportesService(ReportesGestion reportesGestion) {
        this.reportesGestion = reportesGestion;
    }

    // obtener todos los reportes
    @GetMapping
    public List<ReporteModel> getReportes() {
        return reportesGestion.getReportes();
    }

    // agregar un nuevo reporte
    @PostMapping
    public ReporteModel addReporte(@RequestBody ReporteModel reporte) {
        
        
        try{
            return  reportesGestion.addReporte(reporte);
        } catch (Exception e) {
            System.out.println("Error al agregar el reporte: " + e);
            return null;
        }
    }

    // actualizar un reporte según su id
    @PutMapping
    public ReporteModel updateReporte(@RequestBody ReporteModel reporte) {
        ReporteModel dato  =  reportesGestion.updateReporte(reporte);
        System.out.println(dato);
        return dato;
        
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public boolean deleteReporte(@PathVariable Long id) {
        boolean dato  =  reportesGestion.deleteReporte(id);
        System.out.println(dato);
        return dato;
    }
}
