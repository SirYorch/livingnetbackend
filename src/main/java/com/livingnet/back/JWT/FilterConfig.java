package com.livingnet.back.JWT;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// clase de configuración de filtros, 

@Configuration
public class FilterConfig {

    // urls estáticas sirven para utilizarse en validaciones y funcionamiento de usuarios en WTD filter
    public static String direccionTest = "/test/*";
    public static String direccionReports = "/reports/*";
    public static String direccionValidate = "/validate/*";
    public static String direccionImage = "/image/*";
    public static String direccionUsers = "/users/*";
    public static String direccionReporteVacio = "/generate/*";

    // bean para registrar el filtro
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
                direccionUsers // en caso de más urls con filtro, se colocan aquí 
        );
        return registrationBean;
    }
}
