package com.coding.LojoFundrasing.Services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Repos.DonationRepo;


@Service
public class DonationService {
	@Autowired
	private DonationRepo donrepo;
	
	@Autowired
	private EmailService eservice;
	
	@Autowired
	private DonorService dservice;
	
	public Donation createDonation(Donation d) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
		String strDate = dateFormat.format(d.getDondate()); 
		System.out.println("reccurences query");
		List<Donation> recurrences = donrepo.findbyActBlueIdandCommittee_id(d.getActBlueId(), d.getCommittee().getId());
		if (recurrences.size() > 0) {
			System.out.println("reccurences query: " + recurrences.size());
			for (int i = 0; i < recurrences.size(); i++) {
				System.out.println("  " + recurrences.get(i).getId());
			}
		}
		System.out.println("reccurencedated query");
		List<Donation> recurrencedated = donrepo.findbyActBlueIdandCommittee_idandDate(d.getActBlueId(), d.getCommittee().getId(), d.getDondate(), d.getDonor().getId());
		System.out.println("donation id: " + d.getId());
		//System.out.println("recurrence id: " + recurrencedated.getId());
		if (recurrencedated.size() > 0) {
				System.out.println("d recurring number: " + d.getId());
				//System.out.println("recurrence recurring number: " + recurrencedated.getId());
				for (int j = 0; j < recurrencedated.size(); j++) {
					System.out.println("same donation found but not recurring");
					recurrencedated.get(j).setAmount(d.getAmount());
					recurrencedated.get(j).setDonation_uploader(d.getDonation_uploader());
					recurrencedated.get(j).setEmailDonation(d.getEmailDonation());
					recurrencedated.get(j).setRecurring(d.getRecurring());
					d = recurrencedated.get(j);
					return donrepo.save(d);
				}
		}
		return donrepo.save(d);
	}
	
	public List<Donation> findDonations(){
		return donrepo.findAll();
	}
	
	public Donation findDonationbyId(long id) {
		return donrepo.findById(id).orElse(null);
	}
	public List<Donation> findByDonor(long donor_id) {
		return donrepo.findBydonor(donor_id);
	}
	public List<Donation> findByEmail(long email_id) {
		return donrepo.findByemailDonation(email_id);
	}
	public List<Donation> orderAmounts2(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return donrepo.findByOrderByAmountDesc(startdate, enddate, committee_id);
	}
	public List<Donation> orderAmounts(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return donrepo.findByOrderByAmountAsc(startdate, enddate, committee_id);
	}
	public void delete(long id) {
		donrepo.deleteById(id);
	}
	public List<Donation> DonTest(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, @Param("enddate") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return donrepo.findAllWithDondateAfter(startdate, enddate, committee_id);
	}
	public List<Donation> DonTestAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, @Param("enddate") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return donrepo.findAllWithDondateAfterAsc(startdate, enddate, committee_id);
	}
}
