package mx.uam.springboot.app.negocio.modelo;

import java.io.Serializable;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * 
 * @author Gonzalo Hernández
 * 
 */
@Data
@Document(collection = "Activos")
public class Activos implements Serializable{
	
	@Id
	private String id;
	private Alumno alumno;

	private String trimestre;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
