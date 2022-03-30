package com.uam.aga.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.uam.springboot.app.negocio.modelo.Activos;

/**
 * @author Gonzalo Hernández
 * @apiNote Esta clase porporciona los servicios para poder consumir la colección Activos de la BD
 * @
 */
@Service
public class ActivosService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Metodo para poder contar los alumnos activos de un trimestre actual
	 * y el plan de estudios.
	 * 
	 */
	public int currentStudents(String plan, String trimestre) {
		
		Query query = new Query();
		Query query1 = new Query();
		Query query2 = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(trimestre+"I"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		query1.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(trimestre+"P"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		query2.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(trimestre+"O"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		int cont = (int) mongoTemplate.count(query, Activos.class);
		System.out.println("Activo1 : "+cont);
		int cont1 = (int) mongoTemplate.count(query1, Activos.class);
		System.out.println("Activo2 : "+cont1);
		int cont2 = (int) mongoTemplate.count(query2, Activos.class);
		System.out.println("Activo3 : "+cont2);
		int suma = cont+cont1+cont2;
		
		return suma;
	}
	
	/**
	 * Metodo para poder contar los alumnos activos por trimestre
	 * y el plan de estudios.
	 * 
	 * Se quito el estado. 
	 * 
	 */
	public int currentStudentsTri(String plan, String trimestre) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(trimestre),
				Criteria.where("alumno.PLA").is(plan)
				));

		int cont = (int) mongoTemplate.count(query, Activos.class);
		System.out.println("TRimestre: "+ trimestre+", cont"+cont);
		return cont;
	}
}
