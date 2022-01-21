package mx.uam.springboot.app.negocio.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que define la coleecion Usuarios en la BD 
 * @author gonzalo
 *
 */
@Document(collection = "Usuarios")
@Data

/**
 * Definimos un constructor sin argumetos
 * @author gonzalo
 */
@NoArgsConstructor 

/***
 * Definimos un constructor con argumentos
 * @author gonzalo
 */
@AllArgsConstructor

public class Usuario {
	
	@Id
	private String id;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	
	//Es el correo electronico
	private String username;
	private String password;
	
	private List<Rol>  roles;

}
	