package com.uam.aga.app.controllers;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uam.aga.app.services.AlumnoService;

import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;
import mx.uam.springboot.app.negocio.modelo.dto.Cuadro22DTO;

@RestController
@RequestMapping("/api")
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	
	//PETICIONES GET
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param alumnoId El id asignado en la BD
	 * @return El alumno cuyo id=alumnoId si existe, null en caso contrario
	 */
	@GetMapping("/alumnos/{alumnoId}")
	public Alumno findById(@PathVariable final String alumnoId) {
		return alumnoService.findById(alumnoId);
	}
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param matricula La matrícula del alumno a consultar
	 * @return El alumno cuya MAT=matricula si existe, null en caso contrario
	 */
	@GetMapping(path="/alumnos/matricula/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Alumno> findByMatricula(@PathVariable final String matricula) {
		System.out.println(alumnoService.findByMatricula(matricula));
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.findByMatricula(matricula));
		//return alumnoRepository.findByMAT(matricula);
	}

	/**
	 * Endpoint para realizar una petición GET
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyo atributo PLA=plan y UT_RE=trimestre
	 */
	@GetMapping("/alumnos?plan={plan}&trimestre=20P&sexo=M")
	public List<AlumnoDto> findByPlanAndTrimestre(@PathVariable String plan, @PathVariable String trimestre){
		return alumnoService.findByPlanAndTrimestre(plan, trimestre);
	} 
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param sexo El sexo de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan, SEXO=sexo y UT_RE=trimestre
	 */
	@GetMapping("/alumnos/plan/{plan}/trimestre/{trimestre}/sexo/{sexo}")
	public List<Alumno> findByPlanAndSexoAndTrimestre(@PathVariable final long plan,
			@PathVariable final String sexo,@PathVariable final String trimestre){
				return alumnoService.findByPlanAndSexoAndTrimestre(plan,sexo,trimestre);
	}

	/**
	 * Metodo para agregar alumnos a la BD
	 * */
	@PostMapping("/alumnos")
	public void agregarAlumno(@RequestBody final List<Alumno> alumno){
		alumnoService.saveAll(alumno);
	}
	
	
	
	//PETICIONES PATCH
	
	/**
	 * Metodo para actulizar un registro en la BD de manera
	 * basado en su ID y es parcialmente
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * */
	@PatchMapping("/alumnos/{alumnoId}")
	public void UpdateAlumno(@PathVariable final String alumnoId,
			@RequestBody final Alumno alumno) throws Exception {
		for(final Field campo : Alumno.class.getDeclaredFields()) {
			final String fieldname = campo.getName();
			
			if(fieldname.equals("id")) {
				continue;
			}
			
			final java.lang.reflect.Method getter = Alumno.class.getDeclaredMethod( "get"+StringUtils.capitalize(fieldname));
			final  Object valorCampo = getter.invoke(alumno);
			
			if(Objects.nonNull(valorCampo)) {
				
				alumnoService.UpdateAlumno(alumnoId, fieldname, valorCampo);
			}
		}
	}
	
	/**
	 * Metodo para renombrar las fotografias
	 * @return 
	 */
	@GetMapping(path = "/alumnos/fotos/cambio-nombre", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>  changeNamePhotos() {
		alumnoService.cambiaNombreFotos();
		return ResponseEntity.status(HttpStatus.OK).body("");
		
	}
	
	
	/**
	 * Endpoint para realizar una petición GET 
	 * @return Regresa una lista con el nombre, apellido paterno, apellido materno, estado, plan y sexo de los alumnos inscritos en el trimestre.
	 */
	@GetMapping(path="/alumnos/inscritos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Alumno>> findActiveAlumnos() {
		
		return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findActiveAlumnos());
		
	}
	/**
	 * Endpoint para realizar una petición GET
	 * @return Una lista con dos alumnos existentes
	 */
	@GetMapping(path="/alumnos/verifica",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> comprobandoBD(){
		return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findAnyAlumnos());	
	}	
	
	@GetMapping(path = "/alumnos/reporte-cuenta/nuevo-ingreso-aing", produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Integer> countStudentsActiveByAING(
			@RequestParam (value ="anioIngreso")  String anioIngreso,
			@RequestParam (value = "plan")  String plan){
		return ResponseEntity.status(HttpStatus.OK).body(alumnoService.countStudentsActiveByAING(anioIngreso, plan));
	}
	
	@GetMapping(path = "/alumnos/reporte-cuenta/nuevo-ingreso-trii", produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Cuadro22DTO> countStudentsActiveByTRII(
			@RequestParam (value ="trimI")  String trimI,
			@RequestParam (value ="trimP")  String trimP,
			@RequestParam (value ="trimO")  String trimO,
			@RequestParam (value = "plan")  String plan,
			@RequestParam (value = "anioIngreso")  String anioIngreso){
		Cuadro22DTO cuadro22 = new Cuadro22DTO(); 
		cuadro22.setTrimI(alumnoService.countStudentsActiveByTRII(trimI, plan));
		cuadro22.setTrimP(alumnoService.countStudentsActiveByTRII(trimP, plan));
		cuadro22.setTrimO(alumnoService.countStudentsActiveByTRII(trimO, plan));
		cuadro22.setTotal(alumnoService.countStudentsActiveByAING(anioIngreso, plan));
		return ResponseEntity.status(HttpStatus.OK).body(cuadro22);
	}
	
	@GetMapping(path = "/alumnos/reportes/edad", produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<AlumnoDto>> ageStudent(
			@RequestParam (value ="anioIngreso")  String anioIngreso
			){
		return ResponseEntity.status(HttpStatus.OK).body(alumnoService.returnStudetsDataDateBirth(anioIngreso));
	}
}
