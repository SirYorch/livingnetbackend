package com.livingnet.back.Model;


/**
 * Modelo de solicitud para ubicación.
 * Contiene latitud y longitud como strings.
 */
public class LocationRequest {

    private String latitud;
    private String longitud;


    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    
}