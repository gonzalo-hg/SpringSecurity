package com.uam.aga.app.configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class SwaggerUiConfiguration implements WebMvcConfigurer{
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.
	            addResourceHandler("/swagger-ui/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
	                .resourceChain(false);
	        
	        
	        registry.
	        addResourceHandler("/**")
	            .addResourceLocations("classpath:/frontend/dist/alumnos-app/")
	            .resourceChain(false);
	        
	    }

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
	 
	    }

}
