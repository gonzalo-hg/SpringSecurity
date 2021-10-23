package com.uam.aga.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uam.aga.app.models.Rol;


public interface RoleRepository extends MongoRepository<Rol, String>{
	
	public Rol  findByNombre(String nombre);;
}
