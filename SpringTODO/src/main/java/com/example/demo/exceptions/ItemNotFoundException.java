package com.example.demo.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Item does not exist with that id try again")

public class ItemNotFoundException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
