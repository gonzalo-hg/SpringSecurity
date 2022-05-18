package com.uam.aga.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.uam.aga.app.exceptions.CustomException;
import com.uam.aga.app.exceptions.NotNumberException;

import mx.uam.springboot.app.negocio.modelo.Egresado;

/**
 * Clase para servicios de la coleccion de egresados
 * 
 * @author Gonzalo Hernandez
 *
 */
@Service
public class EgresadoService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Servicio para contar los Egresados de las Licenciaturas de la DCBS 2021
	 * 
	 * @param anio String
	 * @param plan String
	 * @return total int, cantidad graduados
	 * @throws CustomException 
	 * @throws NotNumberException 
	 */
	public int countGraduated(String anio, String plan) throws CustomException {
		Query query = new Query();
		Query query1 = new Query();
		Query query2 = new Query();
		
		if((anio == "" || plan == "")) {
			System.out.println("En el service");
			throw new CustomException("Los datos no pueden ser nulos");
		}
		query.addCriteria(new Criteria().andOperator(Criteria.where("trimestre").is(anio+"P"),
		Criteria.where("alumno.UT_AA").is(anio+"I"), Criteria.where("alumno.PLA").is(plan)));
		query1.addCriteria(new Criteria().andOperator(Criteria.where("trimestre").is(anio+"O"),
				Criteria.where("alumno.UT_AA").is(anio+"P"), Criteria.where("alumno.PLA").is(plan)));
		query2.addCriteria(new Criteria().andOperator(Criteria.where("trimestre").is(anio+"I"),
			Criteria.where("alumno.UT_AA").is(anio+"O"), Criteria.where("alumno.PLA").is(plan)));
		int count = (int) mongoTemplate.count(query, Egresado.class);
		int count1 = (int) mongoTemplate.count(query1, Egresado.class);
		int count2 = (int) mongoTemplate.count(query2, Egresado.class);
		int total = count + count1 + count2;
		return total;

	}

	/**
	 * Alumnos Egresados de las Licenciaturas de la DCBS por Trimestre 2021
	 * 
	 * @param trimestre String, Trimestre en el que alumno egreso.
	 * @param plan      String, Plan de estudios del alumno.
	 * @return count int, Cantidad de alumnos agresados.
	 */
	public int countGraduatedTri(String trimestre, String trimUtaa, String plan) {
		Query query = new Query();

		query.addCriteria(new Criteria().andOperator(Criteria.where("trimestre").is(trimestre),
				Criteria.where("alumno.UT_AA").is(trimUtaa), Criteria.where("alumno.PLA").is(plan)));

		int count = (int) mongoTemplate.count(query, Egresado.class);
		return count;
	}

	public List<Egresado> graduatedData(String trimestre, String plan) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("trimestre").is(trimestre),
				Criteria.where("alumno.PLA").is(plan)));
		List<Egresado> egresados = mongoTemplate.find(query, Egresado.class);
		return egresados;
	}
	
	public boolean isNumeric(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
