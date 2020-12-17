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

import com.example.demo.dto.ItemDto;
import com.example.demo.persistence.domain.Item;
import com.example.demo.service.ItemService;

@SpringBootTest
@ActiveProfiles("prod")
public class ItemControllerTest {
	
	@Autowired
	private ItemController controller;
	
	@MockBean
	private ItemService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private ItemDto mapToDto(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}
	
	private final Item TEST_TASK_1 = new Item(1L, "Milk", false, false);
	private final Item TEST_TASK_2 = new Item(2L, "Bread", false, false);
	private final Item TEST_TASK_3 = new Item(3L, "Eggs", false, false);
	private final Item TEST_TASK_4 = new Item(4L, "Butter", false, false);
	
	private final List<Item> LIST_OF_ITEMS = List.of(TEST_TASK_1,TEST_TASK_2,TEST_TASK_3,TEST_TASK_4);
	
	//CREATE
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TASK_1)).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<ItemDto>(this.mapToDto(TEST_TASK_1), HttpStatus.CREATED))
			.isEqualTo(this.controller.create(TEST_TASK_1));
		verify(this.service, atLeastOnce()).create(TEST_TASK_1);
	}
	
	//READ ONE
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_TASK_1.getId())).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<ItemDto>(this.mapToDto(TEST_TASK_1), HttpStatus.OK))
			.isEqualTo(this.controller.readOne(TEST_TASK_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_TASK_1.getId());
	}
	
	//READ ALL
	@Test
	void readAllTest() throws Exception {
		List<ItemDto> x = LIST_OF_ITEMS.stream().map(this::mapToDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(x);
		assertThat(this.controller.read())
			.isEqualTo(new ResponseEntity<>(x, HttpStatus.OK));
		
		verify(this.service, atLeastOnce()).readAll();
	}
	
	//UPDATE
	@Test
	void updateTest() throws Exception {
		when(this.service.update(mapToDto(TEST_TASK_1), TEST_TASK_1.getId())).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<ItemDto>(this.mapToDto(TEST_TASK_1), HttpStatus.ACCEPTED))
			.isEqualTo(this.controller.update(TEST_TASK_1.getId(), mapToDto(TEST_TASK_1)));
		verify(this.service, atLeastOnce()).update(mapToDto(TEST_TASK_1), TEST_TASK_1.getId());
	}
	
	//DELETE
	@Test
	void deleteTest1() throws Exception {
		when(this.service.delete(TEST_TASK_1.getId())).thenReturn(true);
		assertThat(new ResponseEntity<ItemDto>(HttpStatus.NO_CONTENT))
			.isEqualTo(this.controller.delete(TEST_TASK_1.getId()));
		verify(this.service, atLeastOnce()).delete(TEST_TASK_1.getId());
	}
	
	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_TASK_1.getId())).thenReturn(false);
		assertThat(new ResponseEntity<ItemDto>(HttpStatus.I_AM_A_TEAPOT))
			.isEqualTo(this.controller.delete(TEST_TASK_1.getId()));
		verify(this.service, atLeastOnce()).delete(TEST_TASK_1.getId());
	}
}
