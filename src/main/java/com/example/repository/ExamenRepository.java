package com.example.repository;

import java.util.List;

import com.example.model.Examen;

public interface ExamenRepository {
	
	List<Examen> findAll();
	
	
	Examen save(Examen examen);

}
