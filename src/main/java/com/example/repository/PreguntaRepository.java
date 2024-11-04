package com.example.repository;

import java.util.List;

public interface PreguntaRepository {
	
	List<String> getPreguntasByExamenId(Long id);

}
