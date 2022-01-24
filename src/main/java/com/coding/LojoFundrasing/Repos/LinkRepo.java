package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Link;

@Repository
public interface LinkRepo extends CrudRepository<Link, Long>{
	List<Link>findAll();
	
}
