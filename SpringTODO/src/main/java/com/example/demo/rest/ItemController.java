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

import com.example.demo.dto.ItemDto;
import com.example.demo.persistence.domain.Item;
import com.example.demo.service.ItemService;

@RestController
@CrossOrigin
@RequestMapping("/todo_item") // this is to further define the path
public class ItemController {

	private ItemService service;

	@Autowired
	public ItemController(ItemService service) {
		super();
		this.service = service;
	}

	// Create method
	@PostMapping("/create")
	public ResponseEntity<ItemDto> create(@RequestBody Item item) {
		ItemDto created = this.service.create(item);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
		// http status code - 201 (created)

	}

	// read all method
	@GetMapping("/read")
	public ResponseEntity<List<ItemDto>> read() {
		return ResponseEntity.ok(this.service.readAll());
		// ok - 200
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<ItemDto> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<ItemDto> update(@PathVariable Long id, @RequestBody ItemDto itemDto) {
		return new ResponseEntity<>(this.service.update(itemDto, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ItemDto> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				// no_content - if deleted successfully then should return nothing
				: new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		// if the record isnt found!
	}
}
