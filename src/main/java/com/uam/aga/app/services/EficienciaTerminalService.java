package com.uam.aga.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uam.aga.app.exceptions.CustomException;

import lombok.extern.slf4j.Slf4j;
import mx.uam.springboot.app.negocio.modelo.Alumno;

@Slf4j
@Service
public class EficienciaTerminalService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public int getEficienciaTerminalCo(String pla, String aTrii, String aUtaa) throws CustomException {
		
		Query query = new Query();

		try {
			
			if(pla.equals("") || aTrii.equals("") || aUtaa.equals("")) {
				throw new CustomException("Los campos no pueden ser nulos");
			}
			
		} catch (CustomException e) {
			log.error(e.getMessage());
		}
		
		
		query.addCriteria(new Criteria()
				.andOperator(
					Criteria.where("PLA").is(pla),
					Criteria.where("TRII").is(aTrii + "O"),
					Criteria.where("UT_AA").is(aUtaa + "P"))
				.orOperator(
						Criteria.where("EDO").is("5"),
						Criteria.where("EDO").is("6"),
						Criteria.where("EDO").is("12"))
				);
		
		System.out.println("Query: " + query);
		
		int cantidadAlumnos = (int) mongoTemplate.count(query, Alumno.class);
		
		return cantidadAlumnos;
	}
}
