package com.uam.aga.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uam.aga.app.exceptions.CustomException;

import lombok.extern.slf4j.Slf4j;
import mx.uam.springboot.app.negocio.modelo.Alumno;

/**
 * 
 * @author gonza
 *
 */
@Slf4j
@Service
public class EficienciaTerminalService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Servicio que devuelve la cantidad de alumnos egresados con eficiencia
	 * terminal cohorte
	 * 
	 * @param pla   String
	 * @param aTrii String
	 * @param aUtaa String
	 * @return cantidad de alumnos egresados por cohorte
	 * @throws CustomException
	 */
	public int getEficienciaTerminalCo(String pla, String aTrii, String aUtaa) throws CustomException {

		Query query = new Query();

		try {

			if (pla.equals("") || aTrii.equals("") || aUtaa.equals("")) {
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
						Criteria.where("EDO").is("12")));

		int cantidadAlumnos = (int) mongoTemplate.count(query, Alumno.class);

		return cantidadAlumnos;
	}

	/**
	 * Servicio para ver el numero de alumnos adminitos por año
	 * 
	 * @param anio String
	 * @param plan String
	 * @return numero de alumnos admitidos por año
	 */
	public int getAdminitosAnio(String anio, String plan) {

		Query query = new Query();
		
		try {
			if (anio.equals("") || plan.equals("")) {
				throw new CustomException("Los campos no pueden ser nulos");
			}

		} catch (CustomException e) {
			log.error(e.getMessage());
		}
		query.addCriteria(new Criteria().
				andOperator(
						Criteria.where("AING").is(anio), 
						Criteria.where("PLA").is(plan)));
		int alumnosAdimitidos = (int) mongoTemplate.count(query, Alumno.class);
		return alumnosAdimitidos;
	}
	
	/**
	 * Servicio que consulta el numero de alumnos egresados con una
	 * eficiencia terminal FLEXIBLE
	 * @param plan
	 * @param aing
	 * @return
	 */
	public int getEficienciaTerminalFlex(String plan, String aing) {
		
		Query query = new Query();
		
		try {
			if (aing == null || plan == null) {
				throw new CustomException("Los campos no pueden ser nulos");
			}

		} catch (CustomException e) {
			log.error(e.getMessage());
		}
		
		query.addCriteria(new Criteria()
				.andOperator(
						Criteria.where("PLA").is(plan),
						Criteria.where("TRII").is(aing + "O"))
				.orOperator(
						Criteria.where("NTRC").is(13),
						Criteria.where("NTRC").is(14),
						Criteria.where("NTRC").is(15),
						Criteria.where("UT_AA").is((Integer.parseInt(aing) +4) + "O"),
						Criteria.where("UT_AA").is((Integer.parseInt(aing) +5) + "I"),
						Criteria.where("UT_AA").is((Integer.parseInt(aing) +5) + "P"))
				);
		System.out.println(query);
		int cantidadAlumnos = (int) mongoTemplate.count(query, Alumno.class);
		
		return cantidadAlumnos;
	}
}
