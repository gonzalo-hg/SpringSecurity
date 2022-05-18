package com.uam.aga.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uam.aga.app.exceptions.CustomException;
import com.uam.aga.app.services.EficienciaTerminalService;

@RestController
@RequestMapping("/api")
public class EficienciaTerminalController {
	
	@Autowired
	private EficienciaTerminalService eficienciaTerminalService;
	/*db.Alumnos.find({$and:[
    {PLA:'52'},
    {TRII:'11O'},
    {UT_AA:'15P'},
    {$or:[{EDO:'5'},
          {EDO:'6'},
          {EDO:'12'}]}]}).count()*/
	@GetMapping(path = "/alumnos/eficiencia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getEficienciaTerminalCo(
			@RequestParam(value = "plan") String plan,
			@RequestParam(value = "trii") String trii,
			@RequestParam(value = "utaa") String utaa) throws CustomException {

		return ResponseEntity.status(HttpStatus.OK).body(eficienciaTerminalService.getEficienciaTerminalCo(plan,trii,utaa));
	}

}
