package com.livingnet.back;

import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Gestion.ReportesGestion;

import java.util.List;



@RestController
@RequestMapping("/reportes")


//clase de servicio, maneja la recepción y envío de datos mediante solicitudes de usuario
public class ReportesService {

    // obtener todos los reportes
    @GetMapping
    public List<ReporteModel> getReportes() {
        return ReportesGestion.getReportes();
    }

    // agregar un nuevo reporte
    @PostMapping
    public ReporteModel addReporte(@RequestBody ReporteModel reporte) {
        return ReportesGestion.addReporte(reporte);
    }

    // actualizar un reporte según su id
    @PutMapping("/{id}")
    public ReporteModel updateReporte(@PathVariable int id, @RequestBody ReporteModel reporte) {
        return ReportesGestion.updateReporte(id, reporte);
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public boolean deleteReporte(@PathVariable int id) {
        return ReportesGestion.deleteReporte(id);
    }
}
