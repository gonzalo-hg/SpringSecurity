package com.uam.aga.app.services;

import mx.uam.springboot.app.negocio.modelo.Alumno;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AlumnoServiceTest {

    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumno;



    /*@BeforeEach
    void setUp(){
        alumno = new Alumno();
        alumno.setMAT("2153045340");
        alumno.setADEUDO("null");
        alumno.setAING("15/10/21015");
        alumno.setARE("36");
        alumno.setB("5");
        alumno.setANO_TITULA("null");
    }

    @Test
    void findById() {
        when(mongoTemplate.findById(alumno,Alumno.class)).thenReturn(
                (Alumno) Arrays.asList(alumno)
        );
        assertNotNull(alumnoService.findById("2153045340"));
    }*/
}