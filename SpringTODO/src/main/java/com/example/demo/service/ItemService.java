package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ItemDto;
import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.persistence.domain.Item;
import com.example.demo.persistence.repo.ItemRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class ItemService {

	// this is where our business logic will happen

//	this is also where CRUD logic will take place.

	// implements are crud functionality
	private ItemRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}

	@Autowired
	public ItemService(ItemRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public ItemDto create(Item item) {
		return this.mapToDTO(this.repo.save(item));
	}

	// read all method
	public List<ItemDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public ItemDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ItemNotFoundException::new));
	}

	// update
	public ItemDto update(ItemDto itemDto, Long id) {
		// check if record exists
		Item toUpdate = this.repo.findById(id).orElseThrow(ItemNotFoundException::new);
		// set the record to update
		toUpdate.setName(itemDto.getName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(itemDto, toUpdate);
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
