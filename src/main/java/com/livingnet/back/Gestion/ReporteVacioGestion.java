package com.livingnet.back.Gestion;

import com.livingnet.back.DAO.ReporteVacioDAO;
import com.livingnet.back.DAO.UsuarioDAO;
import com.livingnet.back.Model.LocationRequest;
import com.livingnet.back.Model.ReporteVacioModel;
import com.livingnet.back.Model.UsuarioModel;
import com.livingnet.back.Servicios.ImageProcessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReporteVacioGestion {

    @Autowired
    private ReporteVacioDAO reporteVacioDAO;


    @Autowired
    private UsuarioDAO usuarioDAO;

    public ReporteVacioModel generarReporteVacio(Long idUsuario, LocationRequest cuerpo) {
        UsuarioModel user  = usuarioDAO.getUsuarioPorId(idUsuario);
        return reporteVacioDAO.createReporte(idUsuario, cuerpo, user);
    }

    public ReporteVacioModel getReporteVacio(Long idUsuario) {
        return reporteVacioDAO.getReporteVacio(idUsuario);
    }

    public boolean eliminarReporteVacio(Long idUsuario) {
        // eliminar imagen asociada
        ReporteVacioModel rvm = reporteVacioDAO.getReporteVacio(idUsuario);
        if (rvm.getFoto_url() != null && rvm.getFoto_url() != "") {// sirve para eliminar la imagen si esta se cambia
            java.io.File file = new java.io.File(ImageProcessing.UPLOAD_DIR + rvm.getFoto_url().toLowerCase());
            if (file.exists()) {
                file.delete();
            }
        }
        return reporteVacioDAO.deleteReporteVacio(idUsuario);
    }

    public ReporteVacioModel actualizarReporteVacio(ReporteVacioModel rpm, Long idUsuario,String uploaded) {

        if ((rpm.getFoto_url() != null && rpm.getFoto_url() != "" )&&( uploaded != null && uploaded != "")) {// sirve para eliminar la imagen si esta se cambia
            java.io.File file = new java.io.File(ImageProcessing.UPLOAD_DIR + rpm.getFoto_url().toLowerCase());
            if (file.exists()) {
                file.delete();
            }
        }

        ReporteVacioModel reporte = getReporteVacio(idUsuario);

        // fechas y ubicacion no van

        // Strings principales
        reporte.setAgencia(rpm.getAgencia());
        reporte.setActividad(rpm.getActividad());
        reporte.setCuadrilla(rpm.getCuadrilla());
        reporte.setJefe_cuadrilla(rpm.getJefe_cuadrilla());
        reporte.setTipo_actividad(rpm.getTipo_actividad());
        reporte.setFormato_actividad(rpm.getFormato_actividad());
        reporte.setComplejidad_actividad(rpm.getComplejidad_actividad());
        reporte.setEstado_actividad(rpm.getEstado_actividad());
        reporte.setClima(rpm.getClima());
        reporte.setFoto_url(rpm.getFoto_url());

        // Strings opcionales
        reporte.setAyudante_tecnico(rpm.getAyudante_tecnico());
        reporte.setMotivo_retraso(rpm.getMotivo_retraso());
        reporte.setObservaciones(rpm.getObservaciones());

        // Doubles
        reporte.setKilometraje_inicio(rpm.getKilometraje_inicio());
        reporte.setKilometraje_fin(rpm.getKilometraje_fin());

        // Ints
        reporte.setRouter(rpm.getRouter());
        reporte.setOnu(rpm.getOnu());
        reporte.setDrop(rpm.getDrop());
        reporte.setRoseta(rpm.getRoseta());
        reporte.setTensores(rpm.getTensores());
        reporte.setConectores(rpm.getConectores());
        reporte.setCamara(rpm.getCamara());

        if( uploaded != "" ){
            reporte.setFoto_url(uploaded);
        } else if (rpm.getFoto_url() != null && rpm.getFoto_url() != "") {
            reporte.setFoto_url(rpm.getFoto_url());
        } else {
            reporte.setFoto_url(null);
        }


        return reporteVacioDAO.actualizarReporteVacio(reporte);
    }
}
