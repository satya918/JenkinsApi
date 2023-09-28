package com.Jenkins_Api_application.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration {
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	            	   registry.addMapping("/**")
	                   .allowedOrigins("http://localhost:3000") // Replace with your frontend's domain
	                   .allowedMethods("GET", "POST", "PUT", "DELETE")
	                   .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
	                   .allowCredentials(true)
	                   .maxAge(3600); // Max age of preflight request
	            }
	        };
	    }

}
