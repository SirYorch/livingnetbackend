package com.livingnet.back.Servicios;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// clase de servicio de usuarios, maneja las solicitudes HTTP
@RestController
@RequestMapping("/validate")
public class Validation {

    @GetMapping
    public String validate() {
        System.out.println("Validado");
        return "Validación exitosa";
    }
}
