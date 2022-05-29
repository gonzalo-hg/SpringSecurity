package com.uam.aga.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uam.aga.app.services.UsuarioServiceImpl;

import mx.uam.springboot.app.negocio.modelo.Usuario;


@SpringBootApplication
public class SpringSecurityDosApplication {

	public static void main(String[] args)  {
		SpringApplication.run(SpringSecurityDosApplication.class, args);
	
		
	}
	
	/**
	 * 
	 * mvnw package -Dmaven.test.failure.ignore=true && java -jar target/gs-spring-boot-docker-0.1.0.jar
	 * Se crea un componente que va a utilizar spring para codificar el password
	 * @return 
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean
	CommandLineRunner run(UsuarioServiceImpl usuarioService) {
		return args ->{
			Usuario t2 = Usuario.builder()
					.nombre("t2")
					.apellidoP("1234")
					.apellidoM("1234")
					.username("t2")
					.password("1234")
					.roles(new ArrayList<>())
					.build();
			usuarioService.saveUsuario(t2);
			usuarioService.addRolUsuario("t2", "admin");
		};
	}*/
	
	/*
	@Bean
	CommandLineRunner run(UsuarioServiceImpl usuarioService) {
		return args ->{
			usuarioService.saveRole(new Rol("admin"));
			usuarioService.saveRole(new Rol("user"));
			Usuario gozalo = Usuario.builder()
					.nombre("gonzalo")
					.apellidoP("1234")
					.apellidoM("1234")
					.username("zalo1")
					.password("1234")
					.roles(new ArrayList<>())
					.build();
			Usuario ulises = Usuario.builder()
					.nombre("Ulises")
					.apellidoP("Lemarroy")
					.apellidoM("Jiménez")
					.username("ulises1")
					.password("1234")
					.roles(new ArrayList<>())
					.build();
			Usuario antonio = Usuario.builder()
					.nombre("Antonio")
					.apellidoP("Mendoza")
					.apellidoM("Null")
					.username("anton1")
					.password("1234")
					.roles(new ArrayList<>())
					.build();
			Usuario prueba = Usuario.builder()
					.nombre("prueba")
					.apellidoP("pruebaP")
					.apellidoM("pruebaM")
					.username("prueba1@gmail.com")
					.password("1234")
					.roles(new ArrayList<>())
					.build();
			usuarioService.saveUsuario(gozalo);
			usuarioService.saveUsuario(ulises);
			usuarioService.saveUsuario(antonio);
			usuarioService.saveUsuario(prueba);
			usuarioService.addRolUsuario("zalo1", "admin");
			usuarioService.addRolUsuario("zalo1", "user");
			usuarioService.addRolUsuario("ulises1", "admin");
			usuarioService.addRolUsuario("anton1", "admin");
			usuarioService.addRolUsuario("prueba1@gmail.com", "user");
		};
	}*/
	
	
	


}
