package com.examplecom.service;

import java.util.Arrays;
import java.util.List;

import com.example.model.Examen;

public class Datos {
	
	public final static List<Examen> EXAMENES = Arrays.asList(new Examen(1L, "Matematica"),
			new Examen(2L, "Historia"),
			new Examen(3L, "Lenguaje"),
			new Examen(4L, "Quimica"),
			new Examen(5L, "Fisica"),
			new Examen(6L, "Arte"),
			new Examen(7L, "Informatica"));
	
	public final static List<String> PREGUNTAS_HISTORIA = Arrays.asList("Pregunta 1",
			"Pregunta 2",
			"Pregunta 3",
			"Pregunta 4",
			"Pregunta 5",
			"Pregunta 6");

}
