package com.example.service;

import java.util.List;

import com.example.model.Examen;

public interface ExamenService {
	
	Examen findExamenByName(String name);
	
	List<Examen> findAll();
	
	Examen findExamenByNameWithQuestions(String name);

}
