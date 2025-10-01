package com.livingnet.back.Model;

/**
 * DTO para registrar un usuario con confirmación de contraseña.
 * Clase creada para realizar una doble validación en cambios de contraseñas, y creación de usuarios.
 */
public class UsuarioRequest {

    /** ID del usuario. */
    private Long id;

    /** Correo electrónico del usuario. */
    private String mail;

    /** Contraseña del usuario. */
    private String password;

    /** Rol del usuario. */
    private String rol;

    /** Confirmación de contraseña, campo agregado para realizar la validación. */
    private String confirmPassword;


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
