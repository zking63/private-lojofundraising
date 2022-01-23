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
	@Query(value = "SELECT * FROM test WHERE committees_id = :committee_id AND testcategory = :testcategory AND varianta = :varianta AND variantb = :variantb", nativeQuery = true)
	Optional<test> findbyTest(String testcategory, Long committee_id, String varianta, String variantb);
	@Query(value = "SELECT * FROM test WHERE committees_id = :committee_id", nativeQuery = true)
	List<test> findTestsbyCommittee(Long committee_id);
	
	//total recipients calculation
	@Query(value = "SELECT SUM(emails.recipients) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Long variantARecipients(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.recipients) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Long variantBRecipients(Long committee_id, Long testid);
	
	//total openers calculation
	@Query(value = "SELECT SUM(emails.openers) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Long variantAOpens(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.openers) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Long variantBOpens(Long committee_id, Long testid);
	
	//total clicks calculation
	@Query(value = "SELECT SUM(emails.clicks) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Long variantAClicks(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.clicks) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Long variantBClicks(Long committee_id, Long testid);
	
	//total donations calculation
	@Query(value = "SELECT SUM(emails.emaildonationcount) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Long variantADonations(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.emaildonationcount) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Long variantBDonations(Long committee_id, Long testid);
	
	//total revenue calculation
	@Query(value = "SELECT SUM(emails.emaildonationsum) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Double variantARevenue(Long committee_id, Long testid);
	
	@Query(value = "SELECT SUM(emails.emaildonationsum) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Double variantBRevenue(Long committee_id, Long testid);
	
	//total averages per donation calculation
	@Query(value = "SELECT AVG(emails.emaildonationsum) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.varianta = emails.variant", nativeQuery = true)
	Double variantAaverage(Long committee_id, Long testid);
	
	@Query(value = "SELECT AVG(emails.emaildonationsum) FROM emailgroups LEFT JOIN emails on emailgroups.id = emails.emailgroup_id RIGHT JOIN test on emailgroups.test_id = test.id WHERE test.id = :testid AND test.committees_id = :committee_id AND test.variantb = emails.variant", nativeQuery = true)
	Double variantBaverage(Long committee_id, Long testid);
}
