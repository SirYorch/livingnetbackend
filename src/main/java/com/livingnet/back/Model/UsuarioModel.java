package com.livingnet.back.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un usuario.
 * Mapea a la tabla 'usuarios' en la base de datos.
 * Incluye roles para validaciones y JWT.
 */
@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    /** Roles válidos por el momento, permiten validaciones y tiempo de sesión. Revisar también clase JWTFilter en caso de cambios. */
    public static String ROL_ADMIN = "ADMINISTRADOR";
    public static String ROL_TECNICO = "TECNICO";
    public static String ROL_SECRETARIO = "SECRETARIA";

    /** Variable identificadora autogenerada. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Variables tipo string para almacenar correo y contraseña. */
    @Column(unique = true, nullable = false)
    private String mail;

    @Column(unique = false, nullable = false)
    private String password;

    /** Por el momento se aceptan solo las especificadas en variables estáticas. */
    @Column(unique = false, nullable = false)
    private String rol;




    // getters y setters, no es necesario seguir bajando.
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
