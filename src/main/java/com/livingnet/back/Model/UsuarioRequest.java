package com.livingnet.back.Model;

// DTO para registrar un usuario con confirmación de contraseña
//clase creada para realizar una doble validación en cambios de contraseñas, y creación de usuarios.


public class UsuarioRequest {    


    private Long id;
    private String mail;
    private String password;
    private String rol;
    private String confirmPassword; // este es el campo que agregamos para realizar la validación.


    // getters y setters.

   

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
   
}
