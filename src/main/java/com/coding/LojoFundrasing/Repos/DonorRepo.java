package com.coding.LojoFundrasing.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Emails;


@Repository
public interface DonorRepo extends CrudRepository<Donor, Long>{
	List<Donor> findAll();
	Optional<Donor> findBydonorEmail(String email);
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.donor_email = :email", nativeQuery = true)
	Donor findByemailandCommittee(String email, Long committee_id);
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :id", nativeQuery = true)
	Optional<Donor> findByIDandCommittee(Long id, Long committee_id);
	
	//date functions
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND mostrecent_date >= DATE(:startdate) and mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) order by mostrecent_date Desc", nativeQuery = true)
	List <Donor> findAllWithMostRecent(@Param("startdate") @DateTimeFormat(pattern="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern="yyyy-MM-dd") String enddate, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND mostrecent_date >= DATE(:startdate) and mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) order by mostrecent_date Asc", nativeQuery = true)
	List <Donor> findAllWithMostRecentDondateAfterAsc(@Param("startdate") @DateTimeFormat(pattern="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern="yyyy-MM-dd") String enddate, Long committee_id);
	
	//average functions
	@Query(value = "SELECT AVG(donations.amount) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid", nativeQuery = true)
	Double donoraverages(@Param("donorid") Long id, Long committee_id);

	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donoraverage DESC", nativeQuery = true)
	List<Donor> findByDonorAverageByDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donoraverage ASC", nativeQuery = true)
	List<Donor> findByDonorAverageByAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//average within range functions
	@Query(value = "SELECT AVG(donations.amount) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid AND donations.dondate >= DATE(:startdate) and donations.dondate < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY)", nativeQuery = true)
	Double donoravgRange(@Param("donorid") Long id, @Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//sum functions
	@Query(value = "SELECT SUM(donations.amount) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid", nativeQuery = true)
	Double donorsums(@Param("donorid") Long id, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donorsum ASC", nativeQuery = true)
	List<Donor> findByDonorsumByAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donorsum DESC", nativeQuery = true)
	List<Donor> findByDonorsumByDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//sum within rage functions
	@Query(value = "SELECT SUM(donations.amount) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid AND donations.dondate >= :startdate and donations.dondate < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY)", nativeQuery = true)
	Double donorsumRange(@Param("donorid") Long id, @Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//donation count functions
	@Query(value = "SELECT COUNT(DISTINCT donations.id) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid", nativeQuery = true)
	Integer donordoncount(@Param("donorid") Long id, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donor_contributioncount DESC", nativeQuery = true)
	List<Donor> findByContributionCountByDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN data_donors ON donors.id = data_donors.donor_id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) ORDER BY data_donors.donor_contributioncount ASC", nativeQuery = true)
	List<Donor> findByContributionCountByAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//count within range function 
	@Query(value = "SELECT COUNT(DISTINCT donations.id) FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid AND donations.dondate >= DATE(:startdate) and donations.dondate < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) AND donors.mostrecent_date >= DATE(:startdate) and donors.mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY)", nativeQuery = true)
	Integer donordoncountRange(@Param("donorid") Long id, @Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id);
	
	//most recent donation function
	@Query(value = "SELECT donations.id FROM donors LEFT JOIN donations ON donations.donor_id = donors.id RIGHT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND donors.id = :donorid ORDER BY donations.Dondate DESC LIMIT 1", nativeQuery = true)
	Long mostRecentDonationDate(@Param("donorid") Long id, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND mostrecent_date >= DATE(:startdate) and mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) order by mostrecentamount Desc", nativeQuery = true)
	List <Donor> MostrecentamountSortDesc(@Param("startdate") @DateTimeFormat(pattern="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern="yyyy-MM-dd") String enddate, Long committee_id);
	
	@Query(value = "SELECT * FROM donors LEFT JOIN committees ON committees.id = donors.committees_id WHERE committees.id = :committee_id AND mostrecent_date >= DATE(:startdate) and mostrecent_date < DATE_ADD(DATE(:enddate), INTERVAL 1 DAY) order by mostrecentamount Asc", nativeQuery = true)
	List <Donor> MostrecentamountSortAsc(@Param("startdate") @DateTimeFormat(pattern="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern="yyyy-MM-dd") String enddate, Long committee_id);
}