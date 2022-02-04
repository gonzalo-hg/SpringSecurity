package com.uam.aga.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.uam.springboot.app.negocio.modelo.Activos;
import mx.uam.springboot.app.negocio.modelo.Alumno;

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
	public int currentStudents(String plan, String trimestre,String estado) {
		
		Query query = new Query();
		Query query1 = new Query();
		Query query2 = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("alumno.PLA").is(plan),
				Criteria.where("trimestre").is(trimestre+"I")));
		
		query1.addCriteria(new Criteria().andOperator(
				Criteria.where("alumno.PLA").is(plan),
				Criteria.where("trimestre").is(trimestre+"P")));
		
		query2.addCriteria(new Criteria().andOperator(
				Criteria.where("alumno.PLA").is(plan),
				Criteria.where("trimestre").is(trimestre+"O")));
		
		int cont = (int) mongoTemplate.count(query, Activos.class);
		System.out.println("1 "+cont);
		int cont1 = (int) mongoTemplate.count(query1, Activos.class);
		System.out.println("2 "+cont1);
		int cont2 = (int) mongoTemplate.count(query2, Activos.class);
		System.out.println("3 "+cont2);
		int suma = cont+cont1+cont2;
		
		return suma;
	}
	
	/**
	 * Metodo para poder contar los alumnos activos por trimestre
	 * y el plan de estudios.
	 * 
	 */
	public int currentStudentsTri(String plan, String trimestre,String estado) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("alumno.PLA").is(plan),
				Criteria.where("trimestre").is(trimestre),
				Criteria.where("alumno.EDO").is(estado)));

		int cont = (int) mongoTemplate.count(query, Activos.class);
		System.out.println("TRimestre: "+ trimestre+", cont"+cont);
		return cont;
	}
}
