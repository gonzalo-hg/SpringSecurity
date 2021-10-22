package com.uam.aga.app.models;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Usuarios")
@Data @NoArgsConstructor @AllArgsConstructor
public class Usuario {
	
	@Id
	private String id;
	private String nombre;
	private String userName;
	private String password;
	
	
	private Collection<Role> roles = new ArrayList<>();

}
