package com.coding.LojoFundrasing.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Contenttest;

@Repository
public interface ContentTestRepo extends CrudRepository<Contenttest, Long>{
	List<Contenttest> findAll();
	@Query(value = "SELECT * FROM contenttest WHERE committees_id = :committee_id AND jtk = :jtk AND recipients_list = :recipientslist", nativeQuery = true)
	Optional<Contenttest> findbyTest(String recipientslist, String jtk, Long committee_id);
}