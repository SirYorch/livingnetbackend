package com.livingnet.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


/**
 * Clase principal de la aplicación Spring Boot LivingNet Back.
 * Esta clase inicia la aplicación utilizando Spring Boot.
 */
@SpringBootApplication
@RestController
public class BackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
        }


}

