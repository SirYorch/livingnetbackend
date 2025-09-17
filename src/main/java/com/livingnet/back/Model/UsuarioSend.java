package com.livingnet.back.Model;

//clase creada para el envio de información a los usuarios, se omite el campo de contraseñas, para evitar problemas de fugas de datos.
public class UsuarioSend {


    private String mail;
    private String rol;
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
