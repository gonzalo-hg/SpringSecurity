package com.uam.aga.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
/*import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;*/

import com.uam.aga.app.filters.CustomAuthentitcationFilter;
import com.uam.aga.app.filters.CustomAuthorizationFilter;

/***
 * Esta clase permite hacer todas las configuraciones necesarias para decirle a Spring
 * que tenemos nuestras propios usuarios registrados en la BD a los cuales se les hara una autenticacion
 * y una validacion. Es decir se habilita otro tipo de configuracion de seguridad
 * @author gonzalo
 *
 */
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructorextends WebSecurityConfigurerAdapter
public class SecurityConfig {
	
	/**
	 * 
	 */
	//@Autowired
	//private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * La inyeccion de esta clase es para 
	 */
	//@Autowired
	//private  UserDetailsService userDetailsService;
	
	/**
	 * En este metodo anulamos el configure por default. Crea los usuarios y 
	 * el passwordEnconder sirve para codificar la contraseña
	 * @param Nos sirve para risgtrar los usuarios
	 * @throws 
	 */
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}*/
	
		 /*
		 * DEshabilitamos la falsificacion de solicitudes entre sitios	
		 * Luego se crea la politica de gestion de sesisones  httpp
		 * **/
	//@Override
	/*protected void configure(HttpSecurity http) throws Exception {	
		CustomAuthentitcationFilter customAuthentitcationFilter =  new CustomAuthentitcationFilter(authenticationManagerBean());
		
		//Cambiamos el url que tiene por defecto Spring SEcurity, para poder agregar login 
		customAuthentitcationFilter.setFilterProcessesUrl("/api/login");
		http.cors().and();
		http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/","/css/**","/js/**").permitAll();
		http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**","/api/excel/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/usuario/**").hasAnyAuthority("user");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/alumnos/**").hasAnyAuthority("admin");
		//http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthentitcationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}	*/

	/*@Bean
	public AuthenticationManager authenticationManagerBuild() throws Exception{
		return super.authenticationManagerBean();
	}*/
}
