package com.example.demo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.persistence.domain.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

	// it allows us to implement
	// create
	// read
	// update
	// delete

	// custom sql statements e.g. find by make or model .........

}
