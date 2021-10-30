package com.uam.aga.app.security;

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
import filters.CustomAuthentitcationFilter;
import lombok.RequiredArgsConstructor;

/***
 * Esta clase permite hacer todas las configuraciones necesarias para decirle a Spring
 * que tenemos nuestras propios usuarios registrados en la BD a los cuales se les hara una autenticacion
 * y una validacion. Es decir se habilita otro tipo de configuracion de seguridad
 * @author gonzalo
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * La inyeccion de esta clase es para 
	 */
	@Autowired
	private  UserDetailsService userDetailsService;
	
	
	/**
	 * En este metodo anulamos el configure por default
	 */
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
	
		customAuthentitcationFilter.setFilterProcessesUrl("/api/login");
		http.cors().and();//https://www.baeldung.com/spring-cors
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/login/**").permitAll();
		//http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/usuario/**").hasAnyAuthority("user");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/usuario/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/alumnos/solo/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/usuario/guardar/**").hasAnyAuthority("admin");
		
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthentitcationFilter);
	}
	

	@Bean
	public AuthenticationManager authenticationManagerBuild() throws Exception{
		return super.authenticationManagerBean();
	}
	
	

}
