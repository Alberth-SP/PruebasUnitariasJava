package com.examplecom.service;

import static org.mockito.Mockito.*;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.Examen;
import com.example.repository.ExamenRepository;
import com.example.repository.PreguntaRepository;
import com.example.service.*;

//@ExtendWith(MockitoExtension.class) // con esta anotacion no hace falta la linea en setUP
public class ExamenServiceTest {
	
	@Mock
	ExamenRepository repo;
	@Mock
	PreguntaRepository preguntaRepo;
	@InjectMocks
	ExamenServiceImpl service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // adicional a la injeccion de dependencias de mock
		/* instanciamos los mock q usaremos manualmente */
//		this.repo = Mockito.mock(ExamenRepository.class);
//		this.preguntaRepo = Mockito.mock(PreguntaRepository.class);
//		this.service = new ExamenServiceImpl(this.repo, this.preguntaRepo);		
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
	@DisplayName("preguntas Examen")
	void testSizePreguntasByExamen() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepo.getPreguntasByExamenId(2L)).thenReturn(Datos.PREGUNTAS_HISTORIA);
		
		Examen examen = service.findExamenByNameWithQuestions("Historia");
		Assertions.assertNotNull(examen);
		Assertions.assertEquals(6, examen.getQuestions().size());
		verify(repo).findAll();
		verify(preguntaRepo).getPreguntasByExamenId(2L);
	}
	
	@Test
	void testQuestionsZero() {
		Examen examen = new Examen();
		Assertions.assertNotNull(examen);
		Assertions.assertEquals(0, examen.getQuestions().size());
		
		examen.setId(1L);
		examen.setName("Física");
		examen.addQuestion("Como construir una maquina del tiempo?");
		
		Assertions.assertEquals(1L, examen.getId());
		Assertions.assertEquals("Física", examen.getName());
		Assertions.assertEquals(1, examen.getQuestions().size());
		
	}
	
	@Test
	void testSaveExamen() {
		Examen newExam = Datos.EXAMEN;
		newExam.setQuestions(Arrays.asList("Qué es arquitectura", "Que es el Arte?"));
		when(repo.save(any(Examen.class))).thenReturn(Datos.EXAMEN);
		Examen exam = service.save(Datos.EXAMEN);
		
		Assertions.assertNotNull(exam.getId());
		Assertions.assertEquals(6L, exam.getId());
		Assertions.assertEquals("Arte", exam.getName());
		
		verify(preguntaRepo).savePreguntas(anyList());
		verify(repo).save(any(Examen.class));
	}
	
	@Test
	void testFindExamenThrows() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepo.getPreguntasByExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.findExamenByNameWithQuestions("Arte");
		});
		//otra forma
		Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.findExamenByNameWithQuestions("Arte");
		});
		Assertions.assertEquals(IllegalArgumentException.class, ex.getClass());
	}
	
	
	@Test
	void testWithMethodVoid() {
		// util para cuando se quiere mocker un metodo q no devuelve nada
		
		Examen exam = Datos.EXAMEN;
		exam.setQuestions(Datos.PREGUNTAS_HISTORIA);
		
		doThrow(IllegalArgumentException.class).when(preguntaRepo).savePreguntas(anyList());
		exam.getQuestions().forEach(System.out::println);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.save(exam);
		});
				
	}
	
	@Test
	void testDoAnswer() {
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		
		doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			return id == 6L ? Datos.PREGUNTAS_HISTORIA : Collections.emptyList();
			
		}).when(preguntaRepo).getPreguntasByExamenId(anyLong());
		
		Examen exam = service.findExamenByNameWithQuestions("Arte");
		
		Assertions.assertEquals(6L, exam.getId());
		Assertions.assertEquals(6, exam.getQuestions().size());
		Assertions.assertEquals("Arte", exam.getName());
		
	}
	
	
	@Test
	void testOrderCallMock() {
		// asegurando el orden de llamada de los mocks
		when(repo.findAll()).thenReturn(Datos.EXAMENES);
		service.findExamenByNameWithQuestions("Arte");
		service.findExamenByNameWithQuestions("Lenguaje");
		
		InOrder objInOrder = inOrder(repo, preguntaRepo);
		
		objInOrder.verify(repo).findAll();
		objInOrder.verify(preguntaRepo).getPreguntasByExamenId(6L);
		objInOrder.verify(repo).findAll();
		objInOrder.verify(preguntaRepo).getPreguntasByExamenId(3L);
		
		// asegurando el numero de veces que se llama un mock
		verify(repo, times(2)).findAll();
		verify(preguntaRepo, times(2)).getPreguntasByExamenId(anyLong());
	}

}
