package com.coding.LojoFundrasing.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coding.LojoFundrasing.Models.Contenttest;
import com.coding.LojoFundrasing.Models.test;
import com.coding.LojoFundrasing.Models.Contenttest;

public interface testrepo extends CrudRepository<test, Long> {
	List<test> findAll();
	@Query(value = "SELECT * FROM test WHERE committees_id = :committee_id AND testcategory = :testcategory", nativeQuery = true)
	Optional<test> findbyTest(String testcategory, Long committee_id);

}
