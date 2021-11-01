package com.uam.aga.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.uam.aga.app.services.UsuarioService;

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
	CommandLineRunner run(UsuarioService usuarioService) {
		return args ->{
			/*usuarioService.guardarRole(new Rol("admin"));
			usuarioService.guardarRole(new Rol("user"));
			
			usuarioService.guardarUsuario(new Usuario(null, "gonzalo", "zalo1", "1234", new ArrayList<>()));
			usuarioService.guardarUsuario(new Usuario(null, "ulises", "ulises1", "1234", new ArrayList<>()));

			usuarioService.agregaRolUsuario("zalo1", "admin");
			usuarioService.agregaRolUsuario("zalo1", "user");
			usuarioService.agregaRolUsuario("ulises1", "user");
			
			usuarioService.guardarUsuario(new Usuario(null, "antonio", "anton1", "5874", new ArrayList<>()));
			usuarioService.agregaRolUsuario("anton1", "user");*/
			//usuarioService.agregaRolUsuario("anton1", "user");
		};
	}
	


}
