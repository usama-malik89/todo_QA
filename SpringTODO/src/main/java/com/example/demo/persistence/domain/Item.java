package com.example.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
public class Item {

	// default constructor
	// all args constructor
	// getters
	// setters
	// toString
	// equals and hasCode

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	
	@NotNull
	private String category;
	
	@NotNull
	private boolean isDone;
	
	@ManyToOne
	private TodoList list;

	public Item(Long id, String name, String category, boolean isDone) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.isDone = isDone;
	}

	public Item(String name, String category, boolean isDone) {
		super();
		this.name = name;
		this.category = category;
		this.isDone = isDone;
	}


	

}
