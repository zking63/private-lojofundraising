package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.EmailGroup;

@Repository
public interface EmailGroupRepo extends CrudRepository<EmailGroup, Long>{
	List<EmailGroup> findAll();
	@Query(value = "SELECT * FROM emailgroups WHERE emailgroups.id = :id and emailgroups.committees_id = :committee_id", nativeQuery = true)
	EmailGroup findbyIdandCommittee(Long id, Long committee_id);
	@Query(value = "SELECT DISTINCT(emailgroups.id) FROM emailgroups LEFT JOIN emails ON emails.emailgroup_id = emailgroups.id WHERE emailgroups.committees_id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY)", nativeQuery = true)
	List<Long> findGroupByOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
}
