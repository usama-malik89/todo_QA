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

	private ListRepo repo;

	private ModelMapper mapper;

	private ListDto mapToDTO(TodoList todoList) {
		return this.mapper.map(todoList, ListDto.class);
	}

	@Autowired
	public ListService(ListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public ListDto create(TodoList todoList) {
		return this.mapToDTO(this.repo.save(todoList));
	}

	// read all method
	public List<ListDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// read one method
	public ListDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ListNotFoundException::new));
	}

	// update
	public ListDto update(ListDto todoListDto, Long id) {

		TodoList toUpdate = this.repo.findById(id).orElseThrow(ListNotFoundException::new);
		toUpdate.setName(todoListDto.getName());
		SpringBeanUtil.mergeNotNull(todoListDto, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
