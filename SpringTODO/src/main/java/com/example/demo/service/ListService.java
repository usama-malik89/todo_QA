package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ListDto;
import com.example.demo.exceptions.ListNotFoundException;
import com.example.demo.persistence.domain.TodoList;
import com.example.demo.persistence.repo.ListRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class ListService {

	// this is where our business logic will happen

	//this is also where CRUD logic will take place.

	// implements are crud functionality
	private ListRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private ListDto mapToDTO(TodoList todo_list) {
		return this.mapper.map(todo_list, ListDto.class);
	}

	@Autowired
	public ListService(ListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public ListDto create(TodoList todo_list) {
		return this.mapToDTO(this.repo.save(todo_list));
	}

	// read all method
	public List<ListDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public ListDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ListNotFoundException::new));
	}

	// update
	public ListDto update(ListDto todo_listDto, Long id) {
		// check if record exists
		TodoList toUpdate = this.repo.findById(id).orElseThrow(ListNotFoundException::new);
		// set the record to update
		toUpdate.setName(todo_listDto.getName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(todo_listDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// what happenes when you try to merge null stuff?

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}

}
