package com.coding.LojoFundrasing.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coding.LojoFundrasing.Models.Link;
import com.coding.LojoFundrasing.Models.test;

@Repository
public interface LinkRepo extends CrudRepository<Link, Long>{
	List<Link>findAll();
	
	//find link
	@Query(value = "SELECT * FROM links WHERE committees_id = :committee_id AND linkname = :linkname", nativeQuery = true)
	Link findLinkbyNameandCommittee(String linkname, Long committee_id);
	
	//number of emails using link
	@Query(value = "SELECT COUNT(DISTINCT id) FROM emails WHERE committees_id = :committee_id AND link_id = :linkid", nativeQuery = true)
	Long emailscount(Long linkid, Long committee_id);
	
	//number of donations from link
	@Query(value = "SELECT COUNT(DISTINCT donations.id) FROM donations LEFT JOIN emails on donations.email_id = emails.id WHERE emails.committees_id = :committee_id AND emails.link_id = :linkid", nativeQuery = true)
	Integer donationscount(Long linkid, Long committee_id);
	
	//number of donors from link
	@Query(value = "SELECT COUNT(DISTINCT donations.donor_id) FROM donations LEFT JOIN emails on donations.email_id = emails.id WHERE emails.committees_id = :committee_id AND emails.link_id = :linkid", nativeQuery = true)
	Integer donorscount(Long linkid, Long committee_id);
	
	//clicks from email
	@Query(value = "SELECT SUM(clicks) FROM emails WHERE committees_id = :committee_id AND link_id = :linkid", nativeQuery = true)
	Long clicksfromEmailcount(Long linkid, Long committee_id);
}
