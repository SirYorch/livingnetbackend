package com.livingnet.back.Servicios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;
import com.livingnet.back.Gestion.ReportesGestion;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;




@RestController
@RequestMapping("/reports")


//clase de servicio, maneja la recepción y envío de datos mediante solicitudes de usuario
public class ReportesService {

    private final ReportesGestion reportesGestion;

    public ReportesService(ReportesGestion reportesGestion) {
        this.reportesGestion = reportesGestion;
    }

    // obtener todos los reportes
    @GetMapping("/all")
    public List<ReporteModel> getReportes() {
        return reportesGestion.getReportes();
    }
    
    // obtener todos los reportes utilizando los filtros, se recibe un body que no converja con la clase reporteRequest
    @PostMapping("/filters")
    public List<ReporteModel> getReportesFiltros(@RequestBody ReporteRequest body) {
        return reportesGestion.getReportesFiltros(body);
    }

    // obtener cantidad de reportes existentes utilizando los filtros, se recibe un body que no converja con la clase reporteRequest
    @PostMapping("/quantity")
    public Long getCantidadReportes(@RequestBody ReporteRequest body) {
        return reportesGestion.getCantidadReportes(body);
    }

    // actualizar un reporte según su id
    @PutMapping
    public ResponseEntity<ReporteModel> updateReporte(@Valid @RequestBody ReporteModel reporte) {
        try {
            ReporteModel updated = reportesGestion.updateReporte(reporte);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReporte(@PathVariable Long id) {
        Map<String, Boolean> response =  reportesGestion.deleteReporte(id);
        return ResponseEntity.ok(response);
    }

    // metodo para obtener los variables distintivos de los desplegables, sirve para facilitar filtros y rellenados automáticos
    @GetMapping("/deployables")
    public ResponseEntity<Map<String,List<String>>> getDesplegables() {
        Map<String,List<String>> dato = reportesGestion.getDesplegables();
        return ResponseEntity.ok(dato);
    }


}