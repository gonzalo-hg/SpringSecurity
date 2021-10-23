package com.uam.aga.app.models;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "rol")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Rol {

	
	private String nombre;
	
}