package com.uam.aga.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uam.aga.app.services.EgresadoService;

import mx.uam.springboot.app.negocio.modelo.Egresado;
import mx.uam.springboot.app.negocio.modelo.dto.Cuadro22DTO;
/**
 * 
 * @author Gonzalo Hernandez
 *
 */
@RestController
@RequestMapping("/api")
public class EgresadoController {

	@Autowired
	private EgresadoService egresadoService;
	
	/**
	 * 
	 * @param trimestre
	 * @param plan
	 * @return
	 */
	@GetMapping(path="/alumnos/reporte-cuenta/egresados-anio",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> currentStudents(
			@RequestParam (value="trimestre") String trimestre,
			@RequestParam (value="alumnoPlan") String plan) {
		return ResponseEntity.status(HttpStatus.OK).body(egresadoService.countGraduated(trimestre, plan,"",""));
	}
	
	/**
	 * Metodo que sirve para saber cuantos alumnos egresados hay por trimestre
	 * Se toma para el trimestre I el AGA P
	 * Se toma para el trimestre P el AGA O
	 * Se toma para el trimestre O el AGA I del siguiente ciclo. 
	 * @param trimI
	 * @param trimP
	 * @param trimO
	 * @param alumnoPlan
	 * @return
	 */
	@GetMapping(path = "/alumnos/reporte-cuenta/egresados-trii", produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Cuadro22DTO> countCurrentStudentsByTRII(
			@RequestParam (value ="trimI")  String trimI,
			@RequestParam (value ="trimP")  String trimP,
			@RequestParam (value ="trimO")  String trimO,
			@RequestParam (value = "alumnoPlan")  String alumnoPlan)
			{
		Cuadro22DTO cuadro22 = new Cuadro22DTO(); 
		cuadro22.setTrimI(egresadoService.countGraduatedTri(trimP,trimI,alumnoPlan));
		cuadro22.setTrimP(egresadoService.countGraduatedTri(trimO,trimP,alumnoPlan));
		cuadro22.setTrimO(egresadoService.countGraduatedTri(trimI,trimO,alumnoPlan));
		//cuadro22.setTotal(egresadoService.countGraduated(trimestre,alumnoPlan));
		return ResponseEntity.status(HttpStatus.OK).body(cuadro22); 
	}
	
	@GetMapping(path="/alumnos/reporte-cuenta/egresados-datos",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Egresado>> GraduatedDataTri(
			@RequestParam (value="trimestre") String trimestre,
			@RequestParam (value="alumnoPlan") String plan) {
		
		return ResponseEntity.status(HttpStatus.OK).body(egresadoService.graduatedData(trimestre, plan));
	}
}
