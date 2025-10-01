package com.livingnet.back.Servicios;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Servicio de validación de JWT.
 * 
 * Endpoint expuesto en /validate que permite verificar si el token JWT incluido en la petición es válido.
 * La validación real se realiza en los filtros configurados para JWT, por lo que este método solo responde
 * en caso de que la sesión sea válida.
 */
@RestController
@RequestMapping("/validate")
public class Validation {

    // método básico para validar las sesiones, está filtrado y no procesa apenas información.
    @GetMapping
    public String validate() {
        return "Validación exitosa";
    }
}
