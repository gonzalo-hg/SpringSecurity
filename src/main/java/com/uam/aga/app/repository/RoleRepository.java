package com.uam.aga.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uam.aga.app.models.Role;


public interface RoleRepository extends MongoRepository<Role, String>{
	
	public Role  findByNombre(String nombre);;
}
