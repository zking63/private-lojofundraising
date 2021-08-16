package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.coding.LojoFundrasing.Models.test;

public interface testrepo extends CrudRepository<test, Long> {
	List<test> findAll();
}
