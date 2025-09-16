package com.livingnet.back.Servicios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteRequest;
import com.livingnet.back.Gestion.ReportesGestion;

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

    // obtener todos los reportes 2
    @GetMapping("/all")
    public List<ReporteModel> getReportes() {
        return reportesGestion.getReportes();
    }
    
    @PostMapping("/filters")
    public List<ReporteModel> getReportesFiltros(@RequestBody ReporteRequest body) {
        return reportesGestion.getReportesFiltros(body);
    }

    @GetMapping("/quantity")
    public Long getCantidadReportes() {
        return reportesGestion.getCantidadReportes();
    }

    // agregar un nuevo reporte
    @PostMapping
    public ReporteModel addReporte(@RequestBody ReporteModel reporte) {
        if(
            reporte.getActividad() ==null && 
            reporte.getAgencia() ==null && 
            // reporte.getAyudante_tecnico() ==null && 
            reporte.getClima() ==null &&
            reporte.getComplejidad_actividad()==null && 
            reporte.getCuadrilla() ==null && 
            reporte.getEstado_actividad() ==null && 
            reporte.getFecha() ==null && 
            reporte.getFormato_actividad() ==null && 
            reporte.getFoto_url() ==null && 
            reporte.getHorafin() ==null &&
            reporte.getHorainicio() ==null &&
            reporte.getJefe_cuadrilla() ==null &&
            // reporte.getMotivo_retraso() ==null &&
            // reporte.getObservaciones() ==null &&
            reporte.getTipo_actividad() ==null&&
            reporte.getCamara() < 0 && 
            reporte.getConectores()  < 0 && 
            reporte.getDrop()  < 0 &&
            reporte.getKilometraje_fin() < 0 &&
            reporte.getKilometraje_inicio()  < 0 &&
            reporte.getOnu() < 0 &&
            reporte.getRoseta()  < 0 &&
            reporte.getRouter()  < 0 &&
            reporte.getTensores()  < 0 &&
            reporte.getId() != 0
            ){
                try{
                    return  reportesGestion.addReporte(reporte);
                } catch (Exception e) {
                    System.out.println("Error al agregar el reporte: " + e);
                    return null;
                }
            }
            return null;
        
    }

    // actualizar un reporte según su id
    @PutMapping
    public ReporteModel updateReporte(@RequestBody ReporteModel reporte) {
        if(
            reporte.getActividad() ==null && 
            reporte.getAgencia() ==null && 
            // reporte.getAyudante_tecnico() ==null && 
            reporte.getClima() ==null &&
            reporte.getComplejidad_actividad()==null && 
            reporte.getCuadrilla() ==null && 
            reporte.getEstado_actividad() ==null && 
            reporte.getFecha() ==null && 
            reporte.getFormato_actividad() ==null && 
            reporte.getFoto_url() ==null && 
            reporte.getHorafin() ==null &&
            reporte.getHorainicio() ==null &&
            reporte.getJefe_cuadrilla() ==null &&
            // reporte.getMotivo_retraso() ==null &&
            // reporte.getObservaciones() ==null &&
            reporte.getTipo_actividad() ==null&&
            reporte.getCamara() < 0 && 
            reporte.getConectores()  < 0 && 
            reporte.getDrop()  < 0 &&
            reporte.getKilometraje_fin() < 0 &&
            reporte.getKilometraje_inicio()  < 0 &&
            reporte.getOnu() < 0 &&
            reporte.getRoseta()  < 0 &&
            reporte.getRouter()  < 0 &&
            reporte.getTensores()  < 0 &&
            reporte.getId() == 0
            ){
            try{
                ReporteModel dato  =  reportesGestion.updateReporte(reporte);
                System.out.println(dato);
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
}
