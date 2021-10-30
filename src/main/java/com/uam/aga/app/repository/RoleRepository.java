package com.uam.aga.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uam.aga.app.models.Rol;

/***
 * Esta interfaz define los metodos que tendran la conexion con la BD
 * @author gonzalo
 *
 */
public interface RoleRepository extends MongoRepository<Rol, String>{
	
	public Rol  findByNombre(String nombre);;
}
