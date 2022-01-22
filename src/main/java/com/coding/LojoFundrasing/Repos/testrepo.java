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
	@Query(value = "SELECT * FROM test WHERE committees_id = :committee_id", nativeQuery = true)
	List<test> findTestsbyCommittee(Long committee_id);
	
	//total recipients calculation
	@Query(value = "SELECT SUM(emails.recipients) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Long variantARecipients(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.recipients) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Long variantBRecipients(Long committee_id, Long testid);
}
