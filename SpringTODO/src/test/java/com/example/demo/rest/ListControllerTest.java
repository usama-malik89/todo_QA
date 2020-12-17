package com.example.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.ListDto;
import com.example.demo.persistence.domain.TodoList;
import com.example.demo.service.ListService;

@SpringBootTest
@ActiveProfiles("prod")
public class ListControllerTest {
	
	@Autowired
	private ListController controller;
	
	@MockBean
	private ListService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private ListDto mapToDto(TodoList toDoList) {
		return this.mapper.map(toDoList, ListDto.class);
	}
	
	private final TodoList TEST_LIST_1 = new TodoList(1L, "Shopping", "#fffff");
	private final TodoList TEST_LIST_2 = new TodoList(2L, "Work", "#fffff");
	private final TodoList TEST_LIST_3 = new TodoList(3L, "Gym", "#fffff");
	
	private final List<TodoList> LIST_OF_LISTS = List.of(TEST_LIST_1,TEST_LIST_2,TEST_LIST_3);
	
	//CREATE
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_LIST_1)).thenReturn(this.mapToDto(TEST_LIST_1));
		assertThat(new ResponseEntity<ListDto>(this.mapToDto(TEST_LIST_1), HttpStatus.CREATED))
			.isEqualTo(this.controller.create(TEST_LIST_1));
		verify(this.service, atLeastOnce()).create(TEST_LIST_1);
	}
	
	//READ ONE
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_LIST_1.getId())).thenReturn(this.mapToDto(TEST_LIST_1));
		assertThat(new ResponseEntity<ListDto>(this.mapToDto(TEST_LIST_1), HttpStatus.OK))
			.isEqualTo(this.controller.readOne(TEST_LIST_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_LIST_1.getId());
	}
	
	//READ ALL
	@Test
	void readAllTest() throws Exception {
		List<ListDto> x = LIST_OF_LISTS.stream().map(this::mapToDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(x);
		assertThat(this.controller.read())
			.isEqualTo(new ResponseEntity<>(x, HttpStatus.OK));
		
		verify(this.service, atLeastOnce()).readAll();
	}
	
	//UPDATE
	@Test
	void updateTest() throws Exception {
		when(this.service.update(mapToDto(TEST_LIST_1), TEST_LIST_1.getId())).thenReturn(this.mapToDto(TEST_LIST_1));
		assertThat(new ResponseEntity<ListDto>(this.mapToDto(TEST_LIST_1), HttpStatus.ACCEPTED))
			.isEqualTo(this.controller.update(TEST_LIST_1.getId(), mapToDto(TEST_LIST_1)));
		verify(this.service, atLeastOnce()).update(mapToDto(TEST_LIST_1), TEST_LIST_1.getId());
	}
	
	//DELETE
	@Test
	void deleteTest1() throws Exception {
		when(this.service.delete(TEST_LIST_1.getId())).thenReturn(true);
		assertThat(new ResponseEntity<ListDto>(HttpStatus.NO_CONTENT))
			.isEqualTo(this.controller.delete(TEST_LIST_1.getId()));
		verify(this.service, atLeastOnce()).delete(TEST_LIST_1.getId());
	}
	
	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_LIST_1.getId())).thenReturn(false);
		assertThat(new ResponseEntity<ListDto>(HttpStatus.I_AM_A_TEAPOT))
			.isEqualTo(this.controller.delete(TEST_LIST_1.getId()));
		verify(this.service, atLeastOnce()).delete(TEST_LIST_1.getId());
	}
}
