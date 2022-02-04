package com.uam.aga.app.services;

import java.util.List;

import mx.uam.springboot.app.negocio.modelo.Rol;
import mx.uam.springboot.app.negocio.modelo.Usuario;

/**
 * En esta clase se definen todos los metodos que nos ayudaran 
 * a administrar los usuarios
 * @author gonzalo
 *
 */
public interface UsuarioService {

	/**
	 * Metodo que guarda un usuario
	 * @param usuario
	 * @return
	 */
	Usuario saveUsuario(Usuario usuario);
	
	/***
	 * Metodo que guarda un role, es necesario para asignarle 
	 * un rol a un usuario.
	 * @param role
	 * @return
	 */
	Rol saveRole(Rol role);
	
	/***
	 * Asigana al usuario un rol
	 * @param username
	 * @param rolName
	 */
	void addRolUsuario(String username, String rolName);
	
	/***
	 * Metodo que devuelve un usuario
	 * @param username
	 * @return
	 */
	Usuario getUsuario(String username);
	
	/***
	 * Consulta una lista de todos los usuarios
	 * @return
	 */
	List<Usuario>getUsuarios();
}
