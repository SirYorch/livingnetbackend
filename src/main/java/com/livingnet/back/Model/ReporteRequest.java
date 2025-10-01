package com.livingnet.back.Model;

import java.util.Date;


/**
 * Modelo de solicitud para aplicar filtros en las consultas de reportes.
 * Incluye parámetros de paginación y criterios de filtro.
 */
public class ReporteRequest {

    /** Entero para filtrar la cantidad de datos que se envían en el getReportesFiltrado de la clase ReportesDAO. */
    private int datos;

    /** Entero para empezar en otro dígito, si se muestran 10 datos por página, y estamos en la segunda página, se muestran datos desde el 10 hasta el 19 contando el primero como 0. */
    private int pagina;

    /** Filtros válidos en las solicitudes de filtro. */
    private Date fecha;

    private String agencia;
    private String cuadrilla;
    private String jefe_cuadrilla;
    private String ayudante_tecnico;
    private String tipo_actividad;
    private String formato_actividad;
    private String complejidad_actividad;
    private String estado_actividad;
    private String retraso;

    //getters y setters para el funcionamiento de jakarta y las solicitudes sql en reportesDAO

    public int getDatos() {
        return this.datos;
    }

    public void setDatos(int datos) {
        this.datos = datos;
    }

    public int getPagina() {
        return this.pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getCuadrilla() {
        return this.cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getJefe_cuadrilla() {
        return this.jefe_cuadrilla;
    }

    public void setJefe_cuadrilla(String jefe_cuadrilla) {
        this.jefe_cuadrilla = jefe_cuadrilla;
    }

    public String getAyudante_tecnico() {
        return this.ayudante_tecnico;
    }

    public void setAyudante_tecnico(String ayudante_tecnico) {
        this.ayudante_tecnico = ayudante_tecnico;
    }

    public String getTipo_actividad() {
        return this.tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public String getFormato_actividad() {
        return this.formato_actividad;
    }

    public void setFormato_actividad(String formato_actividad) {
        this.formato_actividad = formato_actividad;
    }

    public String getComplejidad_actividad() {
        return this.complejidad_actividad;
    }

    public void setComplejidad_actividad(String complejidad_actividad) {
        this.complejidad_actividad = complejidad_actividad;
    }

    public String getEstado_actividad() {
        return this.estado_actividad;
    }

    public void setEstado_actividad(String estado_actividad) {
        this.estado_actividad = estado_actividad;
    }


    public String getRetraso() {
        return this.retraso;
    }

    public void setRetraso(String retraso) {
        this.retraso = retraso;
    }
   

    
}
