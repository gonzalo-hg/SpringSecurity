package mx.uam.springboot.app.negocio.modelo;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Alumnos")
public class Alumno implements Serializable{

	@Id
	private String id;
	
	private String matricula; 
	private String UNI;
	private String DIV;
	private String NIV;
	private String PLA;
	private String ARE;
	private String RFC;
	private String CAU;
	private String CAD;
	private String CAP;
	private String TUR;
	private String DED;
	private String EDO;
	private String CRE_ACUM;
	private String TRII;
	private String UT_AA;
	private String UT_RE;
	private String NAS;
	private String CRE_PLA;
	private String EDO2;
	private String CRE2;//"CRE2": "500",
	private String TRII2;
	private String UT_AA2;
	private String UT_RE2;
	private String AING;
	private String UA_AA2;
	private String UA_RE2;
	private String NA;
	private String S;
	private String B;
	private String MB;
	private String PROMUAM;
	private String OTRA_CAL;
	private String NTRI;
	private String NTRC;
	private String ESC; //"ESC": "2",
	private String PROM;
	private String FNA;
	private long EDAD;
	private String SEXO;
	private String PUNTAJE;
	private String TEL;
	private String NAL;
	private String EDCAL;
	private String FECNAC;
	private String ADEUDO;
	private String NNTRE;
	private String ANO_TITULA;
	private String UT_IUEA;
	private String UT_IUEA2;
	private String UEA_INS;
	private String CRE_INS;
	private String ORIGEN_MAT;
	private String ED_AL_TIT;
	private String PRORROGA;
	private String FOLIO;
	private String MOTIVO_BAJ;
	private String TRI_UBICA;
	private String CRED_CONTA;
	private String BECA_PRONA;
	private String CRE_EXC;
	private String CRE_MIN;
	private String CRE_MAX;
	private String TRI_CMINCU;
	private String PATE;
	private String MATE;
	private String NOM;
	private String CALLE;
	private String COLONIA;
	private String CODIGOP;
	private String DELEG_MPIO;
	private String LUG_NACIMT;
	private String PORCENTAJE;
	private String FECHA_TITU;
	private String TRI_TITULA;
	private String CORREO_E;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
