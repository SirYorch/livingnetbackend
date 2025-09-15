package com.livingnet.back.JWT;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/test/*");
        registrationBean.addUrlPatterns("/reports/*");
        registrationBean.addUrlPatterns("/validate/*");
        registrationBean.addUrlPatterns("/image/*");
        registrationBean.addUrlPatterns("/users/*");
        return registrationBean;
    }
}