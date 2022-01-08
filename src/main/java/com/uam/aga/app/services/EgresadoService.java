package com.uam.aga.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.uam.springboot.app.negocio.modelo.Egresado;

@Service
public class EgresadoService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/*
	 * Servicio para contar los Egresados de las Licenciaturas de la DCBS 2021
	 *db.Egresados.find({$and:[{"trimestre":"21I"},{"alumno.EDO":"6"}]}).count()
	 *
	 */
	public int countGraduated(String anio, String plan) {
		Query query = new Query();
		Query query1 = new Query();
		Query query2 = new Query();
		
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(anio+"I"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		query1.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(anio+"P"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		query2.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(anio+"O"),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		int count = (int) mongoTemplate.count(query, Egresado.class);
		int count1 = (int) mongoTemplate.count(query1, Egresado.class);
		int count2 = (int) mongoTemplate.count(query2, Egresado.class);
		int total = count + count1 + count2;
		return total;
	}
	
	/**
	 * Alumnos Egresados de las Licenciaturas de la DCBS por Trimestre 2021
	 */
	public int countGraduatedTri(String trimestre, String plan) {
		Query query = new Query();
		Query query1 = new Query();
		Query query2 = new Query();
		
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("trimestre").is(trimestre),
				Criteria.where("alumno.PLA").is(plan)
				));
		
		int count = (int) mongoTemplate.count(query, Egresado.class);

		return count;
	}
}
