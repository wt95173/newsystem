package com.nsystem.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CrosConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","DELETE","PUT","PATCH")
                .maxAge(3600);
    }
}
