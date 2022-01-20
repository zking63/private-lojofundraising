package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Emails;


@Repository
public interface EmailRepo extends CrudRepository<Emails, Long>{
	List<Emails> findAll();
	Emails findByemailRefcode1(String emailRefcode1);
	//find emails without group 
	@Query(value = "SElECT * FROM emails where committees_id = :committee_id and emailgroup_id is NULL AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY)", nativeQuery = true)
	List <Emails> findemailswithoutGroup(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	//find emails by refcodes and commitee
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.email_refcode1 = :emailRefcode AND emails.email_refcode2 = :emailRefcode2", nativeQuery = true)
	Emails findByemailRefcodeandCommittee(String emailRefcode, String emailRefcode2, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.email_refcode1 = :emailRefcode AND (emails.email_refcode2 IS NULL or emails.email_refcode2 = '')", nativeQuery = true)
	Emails findByemailOneRefcodeandCommittee(String emailRefcode, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.email_refcode2 = :emailRefcode2 AND (emails.email_refcode1 IS NULL or emails.email_refcode1 = '')", nativeQuery = true)
	Emails findByemailRefcodeTWOandCommittee(String emailRefcode2, Long committee_id);
	
	//order emails by date
	@Query(value = "SELECT * FROM emails WHERE committees_id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emails.Emaildate Desc", nativeQuery = true)
	List<Emails> findByOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	@Query(value = "SELECT * FROM emails WHERE committees_id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emails.Emaildate Asc", nativeQuery = true)
	List<Emails> findByOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	//average functions
	@Query(value = "SELECT * FROM emails WHERE committees_id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emaildonationaverage Desc", nativeQuery = true)
	List<Emails> findByAverageOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	@Query(value = "SELECT * FROM emails WHERE committees_id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emaildonationaverage Asc", nativeQuery = true)
	List<Emails> findByAverageOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	@Query(value = "SELECT AVG(amount) FROM donations WHERE committees_id = :committee_id AND email_id = :emailid", nativeQuery = true)
	Double averages(@Param("emailid") Long id, Long committee_id);
	
	//sum functions
	@Query(value = "SELECT SUM(donations.amount) FROM emails LEFT JOIN donations ON donations.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.id = :emailid", nativeQuery = true)
	Double sums(@Param("emailid") Long id, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.emailsum Asc", nativeQuery = true)
	List<Emails> findBySumOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.emailsum Desc", nativeQuery = true)
	List<Emails> findBySumOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	
	//donation count
	@Query(value = "SELECT COUNT(DISTINCT donations.id) FROM emails LEFT JOIN donations ON donations.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.id = :emailid", nativeQuery = true)
	Integer donationscount(@Param("emailid") Long id, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.donationcount Asc", nativeQuery = true)
	List<Emails> findByDonationsCountOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.donationcount Desc", nativeQuery = true)
	List<Emails> findByDonationsCountOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	//donor count
	@Query(value = "SELECT COUNT(DISTINCT donations.donor_id) FROM emails LEFT JOIN donations ON donations.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.id = :emailid", nativeQuery = true)
	Integer donorscount(@Param("emailid") Long id, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.donorcount Asc", nativeQuery = true)
	List<Emails> findByDonorCountOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.donorcount Desc", nativeQuery = true)
	List<Emails> findByDonorCountOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	//recurring functions
	@Query(value = "SELECT COUNT(DISTINCT(donations.donor_id)) FROM emails LEFT JOIN donations ON donations.email_id = emails.id WHERE emails.committees_id = :committee_id AND emails.id = :emailid AND (recurring = 'unlimited' OR recurring >= 1)", nativeQuery = true)
	Integer RecurringDonorCount(@Param("emailid") Long id, Long committee_id);
	@Query(value = "SELECT COUNT(donations.id) FROM emails LEFT JOIN donations ON donations.email_id = emails.id WHERE emails.committees_id = :committee_id AND emails.id = :emailid AND (recurring = 'unlimited' OR recurring >= 1)", nativeQuery = true)
	Integer RecurringDonationCount(@Param("emailid") Long id, Long committee_id);
	@Query(value = "SELECT SUM(donations.amount) FROM emails LEFT JOIN donations ON donations.email_id = emails.id WHERE emails.committees_id = :committee_id AND emails.id = :emailid AND (recurring = 'unlimited' OR recurring >= 1)", nativeQuery = true)
	Double RecurringDonationSum(@Param("emailid") Long id, Long committee_id);
}
