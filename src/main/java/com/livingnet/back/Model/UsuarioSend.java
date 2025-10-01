package com.livingnet.back.Model;

/**
 * Clase creada para el envío de información a los usuarios.
 * Se omite el campo de contraseñas, para evitar problemas de fugas de datos.
 */
public class UsuarioSend {

    /** Correo electrónico del usuario. */
    private String mail;

    /** Rol del usuario. */
    private String rol;

    /** ID del usuario. */
    private Long id;



    //getters y setters
    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
