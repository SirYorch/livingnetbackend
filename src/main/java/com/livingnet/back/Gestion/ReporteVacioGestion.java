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
     * Constructor de ReporteVacioGestion.
     * Inyecta las dependencias de ReporteVacioDAO y UsuarioDAO.
     */
    public ReporteVacioGestion() {
    }

    /**
     * Genera un reporte vacío para que cualquier usuario lo pueda ver.
     * @return El ReporteVacioModel creado.
     */
    public ReporteVacioModel generarReporteVacio(ReporteVacioModel cuerpo) {
        
        return reporteVacioDAO.createReporte(cuerpo);
    }

    /**
     * Genera un reporte vacío para un usuario en una ubicación específica.
     * @param idUsuario El ID del usuario.
     * @param cuerpo El LocationRequest con latitud y longitud.
     * @return El ReporteVacioModel creado.
     */
    public ReporteVacioModel generarReporteVacio(Long idUsuario, LocationRequest cuerpo) {
        UsuarioModel user = usuarioDAO.buscarPorId(idUsuario).orElse(null);
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
            ImageProcessing.deleteImagen(rvm.getFoto_url());
        }
        return reporteVacioDAO.deleteReporteVacio(idUsuario);
    }

    /**
     * Elimina un reporte vacío por su ID y su imagen asociada si existe.
     * @param idReporte El ID del reporte a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarReporte(Long idReporte) {
        // eliminar imagen asociada
        ReporteVacioModel rvm = reporteVacioDAO.getReporteVacioById(idReporte);
        if (rvm.getFoto_url() != null && rvm.getFoto_url() != "") {// sirve para eliminar la imagen si esta se cambia
            ImageProcessing.deleteImagen(rvm.getFoto_url());
        }
        return reporteVacioDAO.deleteReporteVacio(idReporte);
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
            ImageProcessing.deleteImagen(rpm.getFoto_url());
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

        // cliente
        reporte.setNombreCliente(rpm.getNombreCliente());
        reporte.setCedulaCliente(rpm.getCedulaCliente());
        reporte.setNumeroContrato(rpm.getNumeroContrato());
        reporte.setTelefonos(rpm.getTelefonos());
        reporte.setCorreo(rpm.getCorreo());
        reporte.setPlan(rpm.getPlan());
        reporte.setCoordenadas(rpm.getCoordenadas());
        reporte.setValorCobrar(rpm.getValorCobrar());

        if( uploaded != "" ){
            reporte.setFoto_url(uploaded);
        } else if (rpm.getFoto_url() != null && rpm.getFoto_url() != "") {
            reporte.setFoto_url(rpm.getFoto_url());
        } else {
            reporte.setFoto_url(null);
        }


        return reporteVacioDAO.actualizarReporteVacio(reporte);
    }

    /**
     * Asigna un reporte vacío a un usuario para que lo pueda llenar.
     * @param idUsuario El ID del usuario.
     * @param cuerpo El LocationRequest con latitud y longitud.
     * @param idReporte El ID del reporte vacío.
     * @return El ReporteVacioModel asignado.
     */
    public ReporteVacioModel assignarReporteVacio(Long idUsuario, LocationRequest cuerpo, Long idReporte) {
        UsuarioModel user = usuarioDAO.buscarPorId(idUsuario).orElse(null);
        return reporteVacioDAO.assignReporte(idReporte, cuerpo, user);
    }

    /**
     * Elimina un reporte vacío sin usuario asociado.
     * @param idReporte El ID del reporte a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarReporteSinUsuario(Long idReporte) {
        return this.reporteVacioDAO.deleteReporteVacioSinUsuario(idReporte);
    }

    /**
     * Edita un reporte vacío sin usuario asociado.
     * @param cuerpo El ReporteVacioModel con los datos actualizados.
     * @return El ReporteVacioModel editado.
     */
    public ReporteVacioModel editReport(ReporteVacioModel cuerpo) {


        return this.reporteVacioDAO.actualizarReporteVacioSinUsuario(cuerpo);
    }
}
