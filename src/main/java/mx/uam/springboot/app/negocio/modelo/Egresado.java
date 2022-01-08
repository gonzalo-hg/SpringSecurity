package mx.uam.springboot.app.negocio.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection ="Egresados")
public class Egresado {

	@Id
	private String id;
	private String trimestre;
	private Alumno alumno;
}
