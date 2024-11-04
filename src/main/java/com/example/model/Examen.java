package com.example.model;

import java.util.List;
import java.util.ArrayList;

public class Examen {
	private Long id;

	private String name;

	private List<String> questions;

	public Examen(Long id, String name) {
		this.name = name;
		this.id = id;
		questions = new ArrayList<String>();
	}

	public Examen() {
		questions = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(String question) {
		this.questions.add(question);
	}

}
