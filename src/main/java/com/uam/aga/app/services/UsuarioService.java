package com.uam.aga.app.services;

import java.util.List;

import com.uam.aga.app.models.Role;
import com.uam.aga.app.models.Usuario;

public interface UsuarioService {

	/**
	 * Metodo que guarda un usuario
	 * @param usuario
	 * @return
	 */
	Usuario guardarUsuario(Usuario usuario);
	
	/***
	 * Metodo que guarda un role, es necesario para asignarle 
	 * un rol a un usuario.
	 * @param role
	 * @return
	 */
	Role guardarRole(Role role);
	
	/***
	 * Asigana al usuario un rol
	 * @param username
	 * @param rolName
	 */
	void agregaRolUsuario(String username, String rolName);
	
	/***
	 * Metodo que devuelve un usuario
	 * @param username
	 * @return
	 */
	Usuario getUsuario(String username);
	
	/***
	 * Consulta una lista de usuarios
	 * @return
	 */
	List<Usuario>getUsuarios();
}
