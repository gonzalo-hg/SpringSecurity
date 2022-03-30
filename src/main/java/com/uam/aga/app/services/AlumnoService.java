package com.uam.aga.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;

/**
 * 
 * @author Gonzalo Hernandez
 *
 */
@Slf4j
@Service
public class AlumnoService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Consulta a un alumno por id
	 * 
	 * @param alumnoId El id asignado en la BD
	 * @return El alumno cuyo id=alumnoId si existe, null en caso contrario
	 */
	public Alumno findById(@PathVariable final String alumnoId) {
		return mongoTemplate.findById(alumnoId, Alumno.class);
	}

	/**
	 * Consulta a un alumno por matricula
	 * 
	 * @param matricula La matrícula del alumno a consultar
	 * @return El alumno cuya MAT=matricula si existe, null en caso contrario
	 */
	@Secured({ "user", "admin" })
	public Alumno findByMatricula(String matricula) {
		Query query = new Query();
		query.addCriteria(Criteria.where("matricula").is(matricula));
		query.fields().include("matricula", "PATE", "MATE", "NOM");
		Alumno alumonosConsulta = mongoTemplate.findOne(query, Alumno.class);
		return alumonosConsulta;
	}

	/**
	 * Consulta a todos los alumnos existentes en la BD
	 * 
	 * @return Una lista con todos los alumnos
	 */
	public List<Alumno> findAll() {
		return mongoTemplate.findAll(Alumno.class);
	}

	/**
	 * Consulta a los alumnos de una carrera dada
	 * 
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyo atributo PLA=plan
	 */
	public List<Alumno> findByPlan(String plan) {
		Query query = new Query();
		query.addCriteria(Criteria.where("PLA").is(plan));
		query.fields().include("NOM", "PLA", "matricula", "PATE", "MATE");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query, Alumno.class);
		return alumonosCoincidentes;
	}

	/**
	 * Consulta a los alumnos filtrados por plan de estudios y último trimestre de
	 * reinscripción
	 * 
	 * @param plan      El plan de estudios de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a
	 *                  consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan y
	 *         UT_RE=trimestre
	 */
	public List<AlumnoDto> findByPlanAndTrimestre(String plan, String trimestre) {
		Query query = new Query();
		query.addCriteria(
				new Criteria().andOperator(Criteria.where("PLA").is(plan), Criteria.where("UT_RE").is(trimestre)));
		query.fields().include("matricula", "PLA", "EDAD", "PATE", "MATE", "NOM");
		// query.fields().elemMatch(plan, Criteria.where("online").is(true));
		List<AlumnoDto> alumnosConsulta = new ArrayList<AlumnoDto>();

		for (Alumno a : mongoTemplate.find(query, Alumno.class)) {
			alumnosConsulta.add(AlumnoDto.creaDto(a));
		}

		return alumnosConsulta;
	}

	/**
	 * Consulta a los alumnos filtrados por plan de estudios, sexo y último
	 * trimestre de reinscripción
	 * 
	 * @param plan      El plan de estudios de los alumnos a consultar
	 * @param sexo      El sexo de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a
	 *                  consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan, SEXO=sexo y
	 *         UT_RE=trimestre
	 */
	public List<Alumno> findByPlanAndSexoAndTrimestre(long plan, String sexo, String trimestre) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("PLA").is(plan), Criteria.where("SEXO").is(sexo),
				Criteria.where("UT_RE").is(trimestre)));
		query.fields().include("matricula", "PLA", "EDAD", "PATE", "MATE", "NOM", "SEXO");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query, Alumno.class);
		// System.out.println("Tamaño de la consulta: " + alumonosCoincidentes.size());
		return alumonosCoincidentes;
	}

	/**
	 * Guarda en la BD a los alumnos recibidos como parámetro
	 * 
	 * @param alumnos La lista de alumnos que se quiere guardar
	 */
	public void saveAll(final List<Alumno> alumnos) {
		mongoTemplate.insertAll(alumnos);
	}

	public List<Alumno> consultaAlumno() {
		Query query = new Query();
		query.addCriteria(
				new Criteria().andOperator(Criteria.where("PLA").is("57"), Criteria.where("UT_RE").is("20P")));
		query.fields().exclude("PLA", "EDAD", "PATE", "MATE", "NOM");

		List<Alumno> alumno = mongoTemplate.find(query, Alumno.class);
		return alumno;
	}

	/**
	 * Actualiza el valor de un atributo de un alumno dado
	 * 
	 * @param alumnoId   El id del alumno en cuestión
	 * @param fieldname  El nombre del campo que se quiere actualizar
	 * @param fieldValue El nuevo valor del campo
	 */
	public void UpdateAlumno(String alumnoId, String fieldname, Object fieldValue) {
		mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(alumnoId)),
				BasicUpdate.update(fieldname, fieldValue), FindAndModifyOptions.none(), Alumno.class);
	}
	
	
	/**
	 * Metodo que sirve para renombrar fotos
	 * 
	 */
	public void cambiaNombreFotos() {
		Query query = new Query();
		File carpetaFotos = new File("D:\\PROYECTO_TERMINAL\\fotos\\FOTOGRAFIAS");
		File[] listaFotos = carpetaFotos.listFiles();
		String[] nombreFotos = new String[listaFotos.length];
		String extensionFoto = null;

		try {
			if (isEmptyDirectory(carpetaFotos)) {
				log.info("The directory: {} is empty", carpetaFotos.getAbsoluteFile());
				throw new Exception("You have upload photos");
			}
			// Verificamos que los archivos contenidos sean jpg
			for (int i = 0; i < listaFotos.length; i++) {
				nombreFotos[i] = FilenameUtils.getBaseName(listaFotos[i].getName());// Seguarda el nombre de la foto
				extensionFoto = FilenameUtils.getExtension(listaFotos[i].getName());// Guardamos la extensionde la foto
				if (extensionFoto.contains("jpg")) {
					query.addCriteria(Criteria.where("matricula").is(nombreFotos[i]));
					query.fields().include("PATE", "MATE", "NOM");
					Alumno alumonosConsulta = mongoTemplate.findOne(query, Alumno.class);
					if (alumonosConsulta != null) {
						File fotoRenombrada = new File("D:\\PROYECTO_TERMINAL\\fotos\\renombradas" + "\\" + alumonosConsulta.getNOM()
								+ "_" + alumonosConsulta.getPATE() + "_" + alumonosConsulta.getMATE() + "."
								+ extensionFoto);
						listaFotos[i].renameTo(fotoRenombrada);
					}
				}
				query = new Query();
			}

		} catch (Exception e) {
			log.info("Upload files to directory");
			log.error("Error charge photos: {}", e.getMessage());
		}

	}

	public static boolean isEmptyDirectory(File directory) throws IOException {
		// Verifica que el directorio existe
		if (directory.exists()) {
			if (!directory.isDirectory()) {
				// throw exception si el path es un archivo
				throw new IllegalArgumentException("Expected directory, but was file: " + directory);
			} else {
				// crea un straeam para checar los archivos
				try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory.toPath())) {
					// regresa false si hay un archivo
					return !directoryStream.iterator().hasNext();
				}
			}
		}
		// regresa trye si no tiene archivos.
		return true;
	}

	/**
	 * Consulta para saber si la BD de Mongo contiene almenos un registro
	 * 
	 * @returnRegresa una lista de alumnos con máximo dos régistros
	 */
	public boolean findAnyAlumnos() {
		Query query = new Query();
		// List<Alumno> alumno = mongoTemplate.count(null,Alumno.class);
		long alumno = mongoTemplate.count(query, Alumno.class);
		// System.out.println("Alumno: " + alumno);
		if (alumno <= 0) {
			// System.out.println("false");
			return false;
		} else {
			// System.out.println("true");
			return true;
		}
	}

	/**
	 * Consulta para regresar a los alumnos inscritos en el trimestre, es decir, que
	 * su estado es activo (EDO="1")
	 * 
	 * @return Regresa una lista de alumnos con los atributos matricula, PLA=plan,
	 *         Edad, PATE=apellido paterno, MATE= apellido materno, EDO=estado y
	 *         SEXO
	 */
	public List<Alumno> findActiveAlumnos() {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("EDO").is("1")));
		query.fields().include("matricula", "NOM", "PATE", "MATE", "EDAD", "PLA", "EDO", "SEXO");
		List<Alumno> alumno = mongoTemplate.find(query, Alumno.class);
		return alumno;
	}

	/**
	 * Metodo para consultar la cantidad de alumnos conforme al plan de estudios y
	 * trimestres del 20I, 20P, 20O
	 */
	public int countStudentsActiveByAING(String anioIngreso, String plan) {
		Query query = new Query();
		query.addCriteria(
				new Criteria().andOperator(Criteria.where("AING").is(anioIngreso), Criteria.where("PLA").is(plan)));
		int cont = (int) mongoTemplate.count(query, Alumno.class);
		return cont;
	}

	/**
	 * Metodo para consultar la cantidad de alumnos conforme al plan de estudios y
	 * trimestres del 20I, 20P, 20O
	 */
	public int countStudentsActiveByTRII(String trimIngreso, String plan) {
		Query query = new Query();
		query.addCriteria(
				new Criteria().andOperator(Criteria.where("TRII").is(trimIngreso), Criteria.where("PLA").is(plan)));
		int cont = (int) mongoTemplate.count(query, Alumno.class);
		return cont;
	}

	public List<Alumno> returnAgeStudent(String anioBusqueda) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("AING").is(anioBusqueda)));
		query.fields().include("matricula", "NOM", "PATE", "MATE", "CORREO_E", "PLA", "FNA", "TEL");

		List<Alumno> alumno = mongoTemplate.find(query, Alumno.class);
		// System.out.println(query);
		// calculaEdad();
		return alumno;
	}

	/**
	 * Metodo que devuelve los datos de los estudiantes con una edad de 17 a 24 del
	 * cierto año en especifico, pero la edad es actual.
	 * 
	 * @param anioBusqueda
	 * @return AlumnoDto alumnoDto
	 */
	public List<AlumnoDto> returnStudetsDataDateBirth(String anioBusqueda) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("AING").is(anioBusqueda)));
		query.fields().include("matricula", "NOM", "PATE", "MATE", "CORREO_E", "PLA", "FNA", "TEL");
		List<Alumno> alumnos = mongoTemplate.find(query, Alumno.class);

		List<AlumnoDto> alumnoDTO = new ArrayList<AlumnoDto>();

		for (Alumno alumno : alumnos) {
			if ((getAgeFormatYY(alumno) >= 17) && (getAgeFormatYY(alumno) <= 24)) {
				alumnoDTO.add(AlumnoDto.creaDto(alumno, getAgeFormatYY(alumno)));
			}
		}

		return alumnoDTO;
	}

	/**
	 * Metodo que sirve para calcular la edad de un alumno La fecha de nacimiento
	 * tiene el formato dd/MM/yy
	 * 
	 * @param alumno
	 * @return int edad
	 */
	public int getAgeFormatYY(Alumno alumno) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

		LocalDate fechaNaciemiento = LocalDate.now();
		fechaNaciemiento = LocalDate.parse(alumno.getFNA(), formatter);

		if (fechaNaciemiento.getYear() == 2099 || fechaNaciemiento.getYear() == 2098
				|| fechaNaciemiento.getYear() == 2097) {
			Period edad = Period.between(fechaNaciemiento.minusYears(100), LocalDate.now());
			return edad.getYears();
		}

		Period edad = Period.between(fechaNaciemiento, LocalDate.now());
		return edad.getYears();
	}

	/**
	 * 
	 * @param alumno
	 * @return int edad
	 */
	public int getAgeFormatYYYY(Alumno alumno) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		LocalDate fechaNaciemiento = LocalDate.parse(alumno.getFNA(), formatter);
		Period edad = Period.between(fechaNaciemiento, LocalDate.now());
		return edad.getYears();
	}

}
