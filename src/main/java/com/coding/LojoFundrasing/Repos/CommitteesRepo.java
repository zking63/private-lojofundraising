package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.User;

@Repository
public interface CommitteesRepo extends CrudRepository<Committees, Long>{
	List<Committees> findAll();
	List<Committees> findByusers(long user_id);

}
