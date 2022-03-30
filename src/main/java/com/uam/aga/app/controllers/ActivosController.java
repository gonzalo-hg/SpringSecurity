package com.uam.aga.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uam.aga.app.services.ActivosService;

import mx.uam.springboot.app.negocio.modelo.dto.Cuadro22DTO;

@RestController
@RequestMapping("/api")
public class ActivosController {
	
	@Autowired
	private ActivosService activosService;
	
	/**
	 * Metodo para contar los alumnos de un año en especifico y plan de estudios. 
	 */
	@GetMapping(path="/alumnos/reporte-cuenta/alumnos-activos",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> currentStudents(
			@RequestParam (value="plan") String plan,
			@RequestParam (value="trimestre") String trimestre) {
		return ResponseEntity.status(HttpStatus.OK).body(activosService.currentStudents(plan, trimestre));
	}
	
	/**
	 * Metodo para contar los alumnos por trimestre y año . 
	 */
	/*@GetMapping(path="/alumnos/reporte-cuenta/alumnos-activos-tri",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> currentStudentsTri(
			@RequestParam (value="plan") String plan,
			@RequestParam (value="trimestre") String trimestre,
			@RequestParam (value="edo") String edo) {
		return ResponseEntity.status(HttpStatus.OK).body(activosService.currentStudentsTri(plan, trimestre, edo));
	}*/

	@GetMapping(path = "/alumnos/reporte-cuenta/alumnos-activos-trii", produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Cuadro22DTO> countCurrentStudentsByTRII(
			@RequestParam (value ="trimI")  String trimI,
			@RequestParam (value ="trimP")  String trimP,
			@RequestParam (value ="trimO")  String trimO,
			@RequestParam (value = "plan")  String plan,
			@RequestParam (value = "anio") String anio){
		Cuadro22DTO cuadro22 = new Cuadro22DTO(); 
		cuadro22.setTrimI(activosService.currentStudentsTri(plan, trimI));
		cuadro22.setTrimP(activosService.currentStudentsTri(plan, trimP));
		cuadro22.setTrimO(activosService.currentStudentsTri(plan, trimO));
		
		cuadro22.setTotal(activosService.currentStudents(plan, anio));
		return ResponseEntity.status(HttpStatus.OK).body(cuadro22); 
	}
}
