package com.uam.aga.app.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uam.aga.app.models.Rol;
import com.uam.aga.app.models.Usuario;
import com.uam.aga.app.services.AlumnoService;
import com.uam.aga.app.services.UsuarioServiceImpl;

import lombok.Data;
import mx.uam.springboot.app.negocio.modelo.Alumno;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	/**
	 * Meotodo que devuelve una lista de usuarios mediante 
	 * @return usuarios
	 */
	@GetMapping("/usuario")
	public ResponseEntity<List<Usuario>>getUsuarios(){
		return ResponseEntity.ok().body(usuarioService.getUsuarios());
	}
	
	@PostMapping("/usuario/guardar")
	public ResponseEntity<Usuario>guardarUsuario( @RequestBody Usuario usuario){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/usuario/guardar").toString());
		return ResponseEntity.created(uri).body(usuarioService.saveUsuario(usuario));
	}
	
	@PostMapping("/role/guardar")
	public ResponseEntity<Rol>guardarRol( @RequestBody Rol role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/guardar").toString());
		return ResponseEntity.created(uri).body(usuarioService.saveRole(role));
	}
	
	@PostMapping("/role/agregaUsuario")
	public ResponseEntity<?>agregaRolUsuario( @RequestBody RoleDeUsuario role){
		usuarioService.addRolUsuario(role.getNombreUsuario(), role.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/alumnos/solo")
	public List<Alumno> buscaUno() {
		return alumnoService.consultaAlumno();
	}
	
	
}

@Data
class RoleDeUsuario{
	private String nombreUsuario;
	private String roleName;
}