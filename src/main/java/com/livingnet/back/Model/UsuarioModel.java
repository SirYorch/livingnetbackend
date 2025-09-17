package com.livingnet.back.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//clase de modelo de usuario, aqui solo guardamos datos correo contraseña, y jwt

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    
    public static String ROL_ADMIN = "ADMINISTRADOR";
    public static String ROL_TECNICO = "TECNICO";
    public static String ROL_SECRETARIO = "SECRETARIA";

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(unique = false, nullable = false)
    private String password;
    
    @Column(unique = false, nullable = false)
    private String rol;
//      TECNICO SECRETARIA ADMINISTRADOR

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public UsuarioModel() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    

}
