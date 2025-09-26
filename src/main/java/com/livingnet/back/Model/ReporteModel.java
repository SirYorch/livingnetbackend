package com.livingnet.back.Model;

import java.util.Date;

import com.livingnet.back.JWT.UpperCaseListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad JPA que representa un reporte.
 * Mapea a la tabla 'reportes' en la base de datos.
 * Incluye listener para convertir strings a mayúsculas.
 */
@Entity
@Table(name = "reportes")
@EntityListeners(UpperCaseListener.class)
public class ReporteModel {
    
    // identificador, generado automático
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    //variables de tipo fecha colocados en los reportes
    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;

    @Column(nullable = false)
    @NotNull(message = "La hora de inicio es obligatoria")
    private Date horainicio;

    @Column(nullable = true)
    @NotNull(message = "El lugar de inicio es obligatorio")
    private String latitudInicio;
    @Column(nullable = true)
    @NotNull(message = "El lugar de inicio es obligatorio")
    private String longitudInicio;


    @Column(nullable = true)
    @NotNull(message = "El lugar de inicio es obligatorio")
    private String latitudFin;
    @Column(nullable = true)
    @NotNull(message = "La Lugar de inicio es obligatorio")
    private String longitudFin;



    @Column(nullable = false)
    @NotNull(message = "La hora de fin es obligatoria")
    private Date horafin;

    //variables de String fecha colocados en los reportes
    @Column(nullable = false)
    @NotBlank(message = "La agencia es obligatoria")
    private String agencia;

    @Column(nullable = false)
    @NotBlank(message = "La actividad es obligatoria")
    private String actividad;

    @Column(nullable = false)
    @NotBlank(message = "La cuadrilla es obligatoria")
    private String cuadrilla;

    @Column(nullable = false)
    @NotBlank(message = "El jefe de cuadrilla es obligatorio")
    private String jefe_cuadrilla;  

    @Column(nullable = false)
    @NotBlank(message = "El tipo de actividad es obligatorio")
    private String tipo_actividad;

    @Column(nullable = false)
    @NotBlank(message = "El formato de actividad es obligatorio")
    private String formato_actividad;   

    @Column(nullable = false)
    @NotBlank(message = "La complejidad de actividad es obligatoria")
    private String complejidad_actividad;

    @Column(nullable = false)
    @NotBlank(message = "El estado de actividad es obligatorio")
    private String estado_actividad;

    @Column(nullable = false)
    @NotBlank(message = "El clima es obligatorio")
    private String clima;

    @Column(nullable = true)
    private String foto_url;

    //variables de tipo String que pueden serr enviadas nulas, 
    @Column(nullable = true)
    private String ayudante_tecnico;
    @Column(nullable = true)
    private String motivo_retraso;
    @Column(nullable = true)
    private String observaciones;
    
    // variables de tipo double
    @Column(nullable = false)
    @Min(value = 0, message = "El kilometraje de inicio debe ser mayor o igual a 0")
    private double kilometraje_inicio;

    @Column(nullable = false)
    @Min(value = 0, message = "El kilometraje de fin debe ser mayor o igual a 0")
    private double kilometraje_fin;

    // variables de tipo int
    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de routers debe ser mayor o igual a 0")
    private int router;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de ONU debe ser mayor o igual a 0")
    private int onu;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de drop debe ser mayor o igual a 0")
    private int drop;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de roseta debe ser mayor o igual a 0")
    private int roseta;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de tensores debe ser mayor o igual a 0")
    private int tensores;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de conectores debe ser mayor o igual a 0")
    private int conectores;

    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad de cámaras debe ser mayor o igual a 0")
    private int camara;
    

    public ReporteModel() {}

    //// getters y setters, no es necesario seguir bajando

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHorainicio() {
        return this.horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafin() {
        return this.horafin;
    }

    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getActividad() {
        return this.actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public double getKilometraje_inicio() {
        return this.kilometraje_inicio;
    }

    public void setKilometraje_inicio(double kilometraje_inicio) {
        this.kilometraje_inicio = kilometraje_inicio;
    }

    public double getKilometraje_fin() {
        return this.kilometraje_fin;
    }

    public void setKilometraje_fin(double kilometraje_fin) {
        this.kilometraje_fin = kilometraje_fin;
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

    public String getFoto_url() {
        return this.foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
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

    public String getClima() {
        return this.clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getMotivo_retraso() {
        return this.motivo_retraso;
    }

    public void setMotivo_retraso(String motivo_retraso) {
        this.motivo_retraso = motivo_retraso;
    }

    public int getRouter() {
        return this.router;
    }

    public void setRouter(int router) {
        this.router = router;
    }

    public int getOnu() {
        return this.onu;
    }

    public void setOnu(int onu) {
        this.onu = onu;
    }

    public int getDrop() {
        return this.drop;
    }

    public void setDrop(int drop) {
        this.drop = drop;
    }

    public int getRoseta() {
        return this.roseta;
    }

    public void setRoseta(int roseta) {
        this.roseta = roseta;
    }

    public int getTensores() {
        return this.tensores;
    }

    public void setTensores(int tensores) {
        this.tensores = tensores;
    }

    public int getConectores() {
        return this.conectores;
    }

    public void setConectores(int conectores) {
        this.conectores = conectores;
    }

    public int getCamara() {
        return this.camara;
    }

    public void setCamara(int camara) {
        this.camara = camara;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }



    public String getLatitudInicio() {
        return this.latitudInicio;
    }

    public void setLatitudInicio(String latitudInicio) {
        this.latitudInicio = latitudInicio;
    }

    public String getLongitudInicio() {
        return this.longitudInicio;
    }

    public void setLongitudInicio(String longitudInicio) {
        this.longitudInicio = longitudInicio;
    }

    public String getLatitudFin() {
        return this.latitudFin;
    }

    public void setLatitudFin(String latitudFin) {
        this.latitudFin = latitudFin;
    }

    public String getLongitudFin() {
        return this.longitudFin;
    }

    public void setLongitudFin(String longitudFin) {
        this.longitudFin = longitudFin;
    }

    
}