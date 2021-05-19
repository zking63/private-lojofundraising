package com.coding.LojoFundrasing.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Data;


@Repository
public interface EmailRepo extends CrudRepository<Emails, Long>{
	List<Emails> findAll();
	Emails findByemailRefcode(String emailRefcode);
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.email_refcode = :emailRefcode", nativeQuery = true)
	Emails findByemailRefcodeandCommittee(String emailRefcode, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emails.Emaildate Desc", nativeQuery = true)
	List<Emails> findByOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by emails.Emaildate Asc", nativeQuery = true)
	List<Emails> findByOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.emailaverage Desc", nativeQuery = true)
	List<Emails> findByAverageOrderByDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT * FROM emails LEFT JOIN data_funds ON data_funds.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.Emaildate >= DATE(:startdateE) and emails.Emaildate < DATE_ADD(DATE(:enddateE), INTERVAL 1 DAY) order by data_funds.emailaverage Asc", nativeQuery = true)
	List<Emails> findByAverageOrderByAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id);
	@Query(value = "SELECT AVG(donations.amount) FROM emails LEFT JOIN donations ON donations.email_id = emails.id RIGHT JOIN committees ON committees.id = emails.committees_id WHERE committees.id = :committee_id AND emails.id = :emailid", nativeQuery = true)
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
}
