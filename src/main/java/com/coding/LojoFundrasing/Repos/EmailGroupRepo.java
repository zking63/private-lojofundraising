package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.EmailGroup;

@Repository
public interface EmailGroupRepo extends CrudRepository<EmailGroup, Long>{
	List<EmailGroup> findAll();
}
