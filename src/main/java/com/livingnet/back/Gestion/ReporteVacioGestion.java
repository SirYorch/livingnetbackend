package com.livingnet.back.Gestion;

import com.livingnet.back.DAO.ReporteVacioDAO;
import com.livingnet.back.DAO.UsuarioDAO;
import com.livingnet.back.Model.LocationRequest;
import com.livingnet.back.Model.ReporteVacioModel;
import com.livingnet.back.Model.UsuarioModel;
import com.livingnet.back.Servicios.ImageProcessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Clase de gestión para reportes vacíos.
 * Maneja la lógica de negocio para la creación, obtención, actualización y eliminación de reportes vacíos.
 */
@Service
public class ReporteVacioGestion {

    @Autowired
    private ReporteVacioDAO reporteVacioDAO;


    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Genera un reporte vacío para un usuario en una ubicación específica.
     * @param idUsuario El ID del usuario.
     * @param cuerpo El LocationRequest con latitud y longitud.
     * @return El ReporteVacioModel creado.
     */
    public ReporteVacioModel generarReporteVacio(Long idUsuario, LocationRequest cuerpo) {
        UsuarioModel user  = usuarioDAO.getUsuarioPorId(idUsuario);
        return reporteVacioDAO.createReporte(idUsuario, cuerpo, user);
    }

    /**
     * Obtiene el reporte vacío asociado a un usuario.
     * @param idUsuario El ID del usuario.
     * @return El ReporteVacioModel correspondiente.
     */
    public ReporteVacioModel getReporteVacio(Long idUsuario) {
        return reporteVacioDAO.getReporteVacio(idUsuario);
    }

    /**
     * Elimina el reporte vacío de un usuario y su imagen asociada si existe.
     * @param idUsuario El ID del usuario.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
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

    /**
     * Actualiza un reporte vacío con los nuevos datos proporcionados.
     * @param rpm El ReporteVacioModel con los datos actualizados.
     * @param idUsuario El ID del usuario.
     * @param uploaded La URL de la imagen subida.
     * @return El ReporteVacioModel actualizado.
     */
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
