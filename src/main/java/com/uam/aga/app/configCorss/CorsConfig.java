package com.uam.aga.app.configCorss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Esta será una clase de configuración para definir el tipo de petición que se va a realizar
 * y los los origines de las mismas.
 * @author gonzalo
 *
 */

@Configuration
public class CorsConfig {

	/***
	 * Definimos una variable que almacena la url permitida
	 */
	@Value("${allowed.origin}")
	private String allowedOrigin;
	
	/***
	 * El siguiente artefacto permite personalizar la configuracion de WebMvc
	 * sobreescribimos los origenes de los cuales puede ser llamado el servicio
	 * @return
	 */
	@Bean
	public WebMvcConfigurer getCrosConfiguration() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins(allowedOrigin)
					.allowedMethods("*")
					.allowedHeaders("*");
			}
		};
	}
}
