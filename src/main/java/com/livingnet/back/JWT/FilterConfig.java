package com.livingnet.back.JWT;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    public static String direccionTest = "/test/*";
    public static String direccionReports = "/reports/*";
    public static String direccionValidate = "/validate/*";
    public static String direccionImage = "/image/*";
    public static String direccionUsers = "/users/*";

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter); // usa el bean @Component
        registrationBean.addUrlPatterns(
                direccionTest,
                direccionReports,
                direccionValidate,
                direccionImage,
                direccionUsers
        );
        return registrationBean;
    }
}
