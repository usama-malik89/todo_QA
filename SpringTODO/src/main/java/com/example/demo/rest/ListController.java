package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ListDto;
import com.example.demo.persistence.domain.TodoList;
import com.example.demo.service.ListService;

@RestController
@CrossOrigin
@RequestMapping("/list") // this is to further define the path
public class ListController {

	private ListService service;

	@Autowired
	public ListController(ListService service) {
		super();
		this.service = service;
	}

	// Create method
	@PostMapping("/create")
	public ResponseEntity<ListDto> create(@RequestBody TodoList list) {
		ListDto created = this.service.create(list);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
		// http status code - 201 (created)

	}

	// read all method
	@GetMapping("/read")
	public ResponseEntity<List<ListDto>> read() {
		return ResponseEntity.ok(this.service.readAll());
		// ok - 200
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<ListDto> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<ListDto> update(@PathVariable Long id, @RequestBody ListDto listDto) {
		return new ResponseEntity<>(this.service.update(listDto, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ListDto> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				// no_content - if deleted successfully then should return nothing
				: new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		// if the record isnt found!
	}
}
