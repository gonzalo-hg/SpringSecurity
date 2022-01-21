package com.uam.aga.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.uam.springboot.app.negocio.modelo.Usuario;

/***
 * Esta interfaz define los metodos que tendran la conexion con la BD
 * @author gonzalo
 *
 */
public interface UsuarioRepository extends  MongoRepository<Usuario, String>{

	public Usuario  findByUsername(String username);
	
}
