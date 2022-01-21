package mx.uam.springboot.app.negocio.modelo.dto;


import lombok.Data;
import mx.uam.springboot.app.negocio.modelo.Alumno;

/**
* DTO de alumnos
*
*/
@Data
public class AlumnoDto {
	
	private String matricula;
	private int edad; // No vacío
	private String nombre; // Rango entre 1 - 120
	private String ape_pat; // No vacío
	private String ape_mat; // No vacío
	private String email;
	private String tel;
	private String fechaNacimiento;
	
	public static AlumnoDto creaDto(Alumno alumno, int edad) {
		AlumnoDto dto = new AlumnoDto();
		dto.setMatricula(alumno.getMatricula());
		dto.setNombre(alumno.getNOM());
		dto.setApe_pat(alumno.getPATE());
		dto.setApe_mat(alumno.getMATE());
		dto.setEmail(alumno.getCORREO_E());
		dto.setTel(alumno.getTEL());
		dto.setFechaNacimiento(alumno.getFNA());
		asignaEdad(dto,edad);
		return dto;
	}
	
	public static AlumnoDto creaDto(Alumno alumno) {
		AlumnoDto dto = new AlumnoDto();
		dto.setMatricula(alumno.getMatricula());
		dto.setNombre(alumno.getNOM());
		dto.setApe_pat(alumno.getPATE());
		dto.setApe_mat(alumno.getMATE());
		dto.setEmail(alumno.getCORREO_E());
		dto.setFechaNacimiento(alumno.getFNA());
		return dto;
	}
	
	public static void asignaEdad(AlumnoDto adto, int edad) {
		adto.setEdad(edad);
	}
}