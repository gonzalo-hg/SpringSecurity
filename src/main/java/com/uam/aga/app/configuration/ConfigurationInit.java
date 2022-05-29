package com.uam.aga.app.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigurationInit implements WebMvcConfigurer{
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        
	        
	        registry.
	        addResourceHandler("/**")
	            .addResourceLocations("classpath:/Frontend/")
	            .resourceChain(false);
	        
	    }
	

}
