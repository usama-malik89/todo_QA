package com.example.demo.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.dto.ItemDto;
import com.example.demo.dto.ListDto;
import com.example.demo.persistence.domain.TodoList;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:item-schema.sql", "classpath:list-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "prod")
class ListControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	@Autowired
	private ModelMapper mapper;
	
	private ListDto mapToDto(TodoList todoList) {
		return this.mapper.map(todoList, ListDto.class);
	}
	
	private final String URI = "/list";
	
	//CREATE TEST
	@Test
	void createTest() throws Exception {
		ListDto testDto = mapToDto(new TodoList("Asda", "#fffff"));
		String testDtoAsJson = this.jsonifier.writeValueAsString(testDto);
		RequestBuilder request = post(URI+"/create").contentType(MediaType.APPLICATION_JSON).content(testDtoAsJson);
		
		//System.out.println(testDtoAsJson);
		
		ResultMatcher checkStatus = status().isCreated();
		
		ListDto testSavedDto = mapToDto(new TodoList("Asda", "#fffff"));
		testSavedDto.setId(4L);
		String testSavedDtoAsJSON = this.jsonifier.writeValueAsString(testSavedDto);
		
		ResultMatcher checkBody = content().json(testSavedDtoAsJSON);
		
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
	
	//READ ONE TEST
	@Test
	void readOneTest() throws Exception {
		
		mvc.perform(get(URI+"/read/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("name", is("Shopping")));
	}
	
	
	//READ ALL TEST
	@Test
	void readAllTest() throws Exception {
		
		mvc.perform(get(URI+"/read")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].name", is("Shopping")))
			      .andExpect(jsonPath("$[1].name", is("Work")));
	}
	
	//UPDATE TEST
	@Test
	void updateTest() throws Exception {
		ListDto testDto = mapToDto(new TodoList("Asda", "#fffff"));
		String testDtoAsJson = this.jsonifier.writeValueAsString(testDto);
		RequestBuilder request = put(URI+"/update/1").contentType(MediaType.APPLICATION_JSON).content(testDtoAsJson);
		
		//System.out.println(testDtoAsJson);
		
		ResultMatcher checkStatus = status().isAccepted();
		
		ListDto testSavedDto = mapToDto(new TodoList("Asda", "#fffff"));
		testSavedDto.setId(1L);
		List<ItemDto> itemsList = List.of();
		testSavedDto.setItems(itemsList);
		String testSavedDtoAsJSON = this.jsonifier.writeValueAsString(testSavedDto);
		
		ResultMatcher checkBody = content().json(testSavedDtoAsJSON);
		
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	//DELETE TEST
	@Test
	void deleteTest() throws Exception {
		RequestBuilder request = delete(URI+"/delete/1").contentType(MediaType.APPLICATION_JSON);
		
		//System.out.println(testDtoAsJson);
		
		ResultMatcher checkStatus = status().isNoContent();
		
		this.mvc.perform(request).andExpect(checkStatus);
	}
}
