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
    public ReporteModel updateReporte(@Valid @RequestBody ReporteModel reporte) {
        if(
           reporte.getActividad() !=null && 
            reporte.getAgencia() !=null && 
            reporte.getClima() !=null &&
            reporte.getComplejidad_actividad()!=null && 
            reporte.getCuadrilla() !=null && 
            reporte.getEstado_actividad() !=null && 
            // reporte.getFecha() !=null && 
            reporte.getFormato_actividad() !=null && 
            // reporte.getFoto_url() !=null && 
            // reporte.getHorafin() !=null &&
            // reporte.getHorainicio() !=null &&
            reporte.getJefe_cuadrilla() !=null &&
            reporte.getTipo_actividad() !=null&&
            reporte.getCamara() >= 0 && 
            reporte.getConectores()  >= 0 && 
            reporte.getDrop()  >= 0 &&
            reporte.getKilometraje_fin() >= 0 &&
            reporte.getKilometraje_inicio()  >= 0 &&
            reporte.getOnu() >= 0 &&
            reporte.getRoseta()  >= 0 &&
            reporte.getRouter()  >= 0 &&
            reporte.getTensores()  >= 0 &&
            reporte.getId() != 0 // hora de inicio, hora de fin, y ubicaciones no se editan ni aunque se cambien
            ){
            try{
                ReporteModel dato  =  reportesGestion.updateReporte(reporte);
                return dato;
            } catch (Exception e){
                return null;
            }
        }
        return null;
        
    }

    // eliminar un reporte según su id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReporte(@PathVariable Long id) {
        Map<String, Boolean> response =  reportesGestion.deleteReporte(id);
        return ResponseEntity.ok(response);
    }

    // metodo para obtener los variables distintivos de los desplegables, sirve para facilitar filtros y rellenados automáticos
    @GetMapping("/deployables")
    public ResponseEntity<?> getDesplegables() {
        Map<String,List<String>> dato = reportesGestion.getDesplegables();

        if(dato.size()>0){
            return ResponseEntity.ok(dato);
        } else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("No se consiguieron los deplegables");
        }
    }


}