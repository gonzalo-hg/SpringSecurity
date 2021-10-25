package com.uam.aga.app.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import filters.CustomAuthentitcationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private  UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
		 /*
		 * DEshabilitamos la falsificacion de solicitudes entre sitios	
		 * Luego se crea la politica de gestion de sesisones  httpp
		 * **/
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		CustomAuthentitcationFilter customAuthentitcationFilter =  new CustomAuthentitcationFilter(authenticationManagerBean());
		//Cambiamos el url que tiene por defecto Spring SEcurity, para poder agregar login 
		
		/*CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        
        // You can customize the following part based on your project, it's only a sample
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);
	    */
		
		customAuthentitcationFilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/usuario/**").hasAnyAuthority("user");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/usuario/guardar/**").hasAnyAuthority("admin");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthentitcationFilter);
	}
	

	@Bean
	public AuthenticationManager authenticationManagerBuild() throws Exception{
		return super.authenticationManagerBean();
	}
	
	

}
