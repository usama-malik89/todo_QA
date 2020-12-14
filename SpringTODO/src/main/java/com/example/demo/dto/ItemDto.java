package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

	private Long id;
	private String name;
	private String category;
	private boolean isDone;

	// this will spit out JSON

}
