package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Contenttest;

@Repository
public interface ContentTestRepo extends CrudRepository<Contenttest, Long>{
	List<Contenttest> findAll();
}
