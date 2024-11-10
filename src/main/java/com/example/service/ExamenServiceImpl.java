package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Examen;
import com.example.repository.ExamenRepository;
import com.example.repository.PreguntaRepository;

public class ExamenServiceImpl implements  ExamenService {
	
	ExamenRepository repo;
	
	PreguntaRepository preguntaRepo;
	
	
	public ExamenServiceImpl(ExamenRepository repo, PreguntaRepository preguntaRepo) {
		this.repo = repo;
		this.preguntaRepo = preguntaRepo;
	}

	@Override
	public Examen findExamenByName(String name) {
		
		Optional<Examen> examenOpt = repo.findAll().stream()
				.filter(e -> e.getName().contains(name))
				.findFirst();
		
		Examen examen = null;
		if(examenOpt.isPresent())
			examen = examenOpt.get();
		return examen;
	}

	@Override
	public List<Examen> findAll() {
		return repo.findAll();
	}

	@Override
	public Examen findExamenByNameWithQuestions(String name) {
		
		Examen examen = findExamenByName(name);
		if(examen != null) {
			List<String> preguntas = preguntaRepo.getPreguntasByExamenId(examen.getId());
			examen.setQuestions(preguntas);
		}
		return examen;
	}

	@Override
	public Examen save(Examen examen) {
		
		// TODO Auto-generated method stub
		if(!examen.getQuestions().isEmpty()) {
			System.out.print(">>>TIENE PEGUNTAS");
			preguntaRepo.savePreguntas(examen.getQuestions());
		}
		return this.repo.save(examen);
	}

}
