package com.example.demo.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListDto {

	private Long id;
	private String name;

	private List<ItemDto> items;

}
