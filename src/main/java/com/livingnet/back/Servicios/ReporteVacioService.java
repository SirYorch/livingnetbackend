package com.livingnet.back.Servicios;

import com.livingnet.back.Gestion.ReporteVacioGestion;
import com.livingnet.back.Gestion.ReportesGestion;
// import com.livingnet.back.Gestion.ReportesGestion;
import com.livingnet.back.Model.LocationRequest;
import com.livingnet.back.Model.ReporteModel;
import com.livingnet.back.Model.ReporteVacioModel;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




@RestController
@RequestMapping("/generate")
public class ReporteVacioService {

    @Autowired
    private ReporteVacioGestion reporteVacioGestion;

    @Autowired
    private ImageProcessing procesamiento;

    @Autowired
    private ReportesGestion reportesGestion;

    @GetMapping("/{idUsuario}")
    public ReporteVacioModel getReporteVacio(@PathVariable Long idUsuario) {
        return reporteVacioGestion.getReporteVacio(idUsuario);
    }
    

    // Generar un nuevo reporte vacío para un usuario, agregando la hora del servidor en horaInicio
       @PostMapping("/{idUsuario}")
    public ReporteVacioModel generarReporteVacio(
        @RequestBody LocationRequest cuerpo, 
        @PathVariable Long idUsuario
        ) {
        return reporteVacioGestion.generarReporteVacio(idUsuario, cuerpo);
    }

    @DeleteMapping("/{idUsuario}")
    public boolean eliminarReporteVacio(@PathVariable Long idUsuario){
        return reporteVacioGestion.eliminarReporteVacio(idUsuario);
    }
    
    @PutMapping(value="/{idUsuario}", consumes = {"multipart/form-data"})
    public ReporteVacioModel putMethodName(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart("reporte") ReporteVacioModel rpm,
            @PathVariable Long idUsuario) {
        try {
            String uploaded = "";
            if (file != null && !file.isEmpty()) {
                uploaded = procesamiento.uploadImage(file);
            }
            return reporteVacioGestion.actualizarReporteVacio(rpm,idUsuario, uploaded);
        } catch (Exception e) {
            return null;
        }
    }

    // Agregar un nuevo reporte con imagen subida en el mismo método
    @PostMapping(value = "/reports/{idUsuario}", consumes = {"multipart/form-data"})
    public ResponseEntity<ReporteModel> addReporteWithImage(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Valid @RequestPart("reporte") ReporteVacioModel reporte,
            @PathVariable Long idUsuario) {
    
    // validacion de objetos

            String uploaded = "";
            ReporteModel rm = new ReporteModel();
            if (file != null && !file.isEmpty()) {
                uploaded = procesamiento.uploadImage(file);
                rm.setFoto_url(uploaded);    
            }

            // Validar los campos obligatorios
            boolean valido = reporte.getActividad() != null &&
                    reporte.getAgencia() != null &&
                    reporte.getClima() != null &&
                    reporte.getComplejidad_actividad() != null &&
                    reporte.getCuadrilla() != null &&
                    reporte.getEstado_actividad() != null &&
                    // reporte.getFecha() != null && // se coloca desde acá
                    reporte.getFormato_actividad() != null &&
                    //reporte.getFoto_url() == null&& // debe ser nula
                    // reporte.getHorafin() = null &&
                    // reporte.getHorainicio() != null && 
                    reporte.getJefe_cuadrilla() != null &&
                    reporte.getTipo_actividad() != null &&
                    reporte.getCamara() >= 0 &&
                    reporte.getConectores() >= 0 &&
                    reporte.getDrop() >= 0 &&
                    reporte.getKilometraje_fin() >= 0 &&
                    reporte.getKilometraje_inicio() >= 0 &&
                    reporte.getOnu() >= 0 &&
                    reporte.getRoseta() >= 0 &&
                    reporte.getRouter() >= 0 &&
                    reporte.getTensores() >= 0 &&
                    // reporte.getId() == 0 &&
                    reporte.getLatitudFin() != null &&
                    reporte.getLongitudFin() != null
                    ;

            if (valido) {

                if (uploaded != ""|| reporte.getFoto_url()!= null) {
                    reporte.setFoto_url(uploaded);
                } else {
                    reporte.setFoto_url(null);
                }
                rm = reportesGestion.generarReporte(reporte, idUsuario, rm);


                return ResponseEntity.ok(rm);
            }
            
            // creación de la imagen en la base retorna el valor de nombre


                return ResponseEntity.internalServerError().build();
    }   
}
