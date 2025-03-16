package com.cse681;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    //
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        // public method that will create an instance of WebMvcConfigurer type
        // we will use this to instantiate a registry with CORS property
        // to allow localhost traffic
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                // Applies CORS settings to backend endpoints - /api/
                registry.addMapping("/api/**")
                        // Allow traffic from localhost and port 3000
                        .allowedOrigins("http://localhost:3000")
                        // Allow GET, POST, DELETE, PUT http methods
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        // Allow cookies and http headers
                        .allowCredentials(true);
            }
        };
    }
}
