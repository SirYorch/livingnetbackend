package com.livingnet.back.Servicios;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// clase de servicio de validación, maneja las solicitudes HTTP
@RestController
@RequestMapping("/validate")
public class Validation {

    // método básico para validar las sesiones, está filtrado y no procesa apenas información.
    @GetMapping
    public String validate() {
        return "Validación exitosa";
    }
}
