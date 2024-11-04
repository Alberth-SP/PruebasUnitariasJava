package com.examplecom.service;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.model.Examen;
import com.example.repository.ExamenRepository;
import com.example.repository.PreguntaRepository;
import com.example.service.*;
public class ExamenServiceTest {
	
	ExamenRepository repo;
	PreguntaRepository preguntaRepo;
	ExamenService service;
	
	@BeforeEach
	void setUp() {
		this.repo = Mockito.mock(ExamenRepository.class);
		this.preguntaRepo = Mockito.mock(PreguntaRepository.class);
		this.service = new ExamenServiceImpl(this.repo, this.preguntaRepo);		
	}
	
	
	
	@Test
	@DisplayName("Buscar examen por nombre")
	void testFindExamenByName() {
		
		when(repo.findAll()).thenReturn(Datos.EXAMENES);	
		Examen examen = service.findExamenByName("Historia");
		
		Assertions.assertNotNull(examen);
		Assertions.assertEquals(2L, examen.getId());
		Assertions.assertEquals("Historia", examen.getName());
		
	}
	
	@Test
	@DisplayName("lista vacia")
	void testListaVacia() {
		 
		List<Examen> datos = Collections.emptyList();
		when(repo.findAll()).thenReturn(datos);
		
		List<Examen> examen = service.findAll();
		
		Assertions.assertEquals(0, examen.size());
		
	}
	
	
	@Test
	@DisplayName("Cantidad de preguntas")
	void testSizePreguntasByExamen() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepo.getPreguntasByExamenId(2L)).thenReturn(Datos.PREGUNTAS_HISTORIA);
		
		Examen examen = service.findExamenByNameWithQuestions("Historia");
		Assertions.assertNotNull(examen);
		Assertions.assertEquals(6, examen.getQuestions().size());
	}

}
