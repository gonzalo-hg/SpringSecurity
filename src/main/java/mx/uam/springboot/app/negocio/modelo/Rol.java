package mx.uam.springboot.app.negocio.modelo;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Clase que define la coleccion Rol en la BD 
 * @author gonzalo
 *
 */
@Document(collection = "rol")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Rol {

	private String nombre;
	
}
