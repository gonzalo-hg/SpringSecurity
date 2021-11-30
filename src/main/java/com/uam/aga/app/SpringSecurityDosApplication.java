package com.uam.aga.app;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uam.aga.app.models.Rol;
import com.uam.aga.app.models.Usuario;
import com.uam.aga.app.services.UsuarioService;
import com.uam.aga.app.services.UsuarioServiceImpl;

@SpringBootApplication
public class SpringSecurityDosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDosApplication.class, args);
	}
	
	/**
	 * Se crea un componente que va a utilizar spring para codificar el password
	 * @return 
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UsuarioServiceImpl usuarioService) {
		return args ->{
			//usuarioService.saveRole(new Rol("admin"));
			//usuarioService.saveRole(new Rol("user"));
			
			//usuarioService.saveUsuario(new Usuario(null, "gonzalo", "zalo1", "1234", new ArrayList<>()));
			//usuarioService.saveUsuario(new Usuario(null, "ulises", "ulises1", "1234", new ArrayList<>()));
			//usuarioService.saveUsuario(new Usuario(null, "antonio", "anton1", "5874", new ArrayList<>()));
			//usuarioService.saveUsuario(new Usuario(null, "prueba","pruebaP","pruebaM", "prueba1@gmail.com", "1234", new ArrayList<>()));
			//usuarioService.addRolUsuario("zalo1", "admin");
			//usuarioService.addRolUsuario("zalo1", "user");
			//usuarioService.addRolUsuario("ulises1", "admin");
			//usuarioService.addRolUsuario("anton1", "admin");
		};
	}
	


}
