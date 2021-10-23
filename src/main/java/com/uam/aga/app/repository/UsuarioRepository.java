package com.uam.aga.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uam.aga.app.models.Usuario;


public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	public Usuario  findByUsername(String username);
}
