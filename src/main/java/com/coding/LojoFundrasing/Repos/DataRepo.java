package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Data;





@Repository
public interface DataRepo extends CrudRepository<Data, Long>{
	List<Data>findAll();
}
