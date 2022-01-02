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
			@RequestParam (value="trimestre") String trimestre,
			@RequestParam (value="edo") String edo) {
		return ResponseEntity.status(HttpStatus.OK).body(activosService.currentStudents(plan, trimestre, edo));
	}

}
