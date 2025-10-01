package com.livingnet.back.JWT;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para filtros JWT.
 * Configura el registro del filtro JWT para proteger ciertas rutas.
 */
@Configuration
public class FilterConfig {

    // urls estáticas sirven para utilizarse en validaciones y funcionamiento de usuarios en WTD filter
    public static String direccionTest = "/test/*";
    public static String direccionReports = "/reports/*";
    public static String direccionValidate = "/validate/*";
    public static String direccionImage = "/image/*";
    public static String direccionUsers = "/users/*";
    public static String direccionUsersRoot = "/users";
    public static String direccionReporteVacio = "/generate/*";

    /**
     * Bean para registrar el filtro JWT.
     * @param jwtFilter El filtro JWT a registrar.
     * @return El FilterRegistrationBean configurado.
     */
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns(
                direccionTest,
                direccionReports,
                direccionValidate,
                direccionImage,
                direccionReporteVacio,
                direccionUsers, // en caso de más urls con filtro, se colocan aquí 
                direccionUsersRoot
        );
        return registrationBean;
    }
}
