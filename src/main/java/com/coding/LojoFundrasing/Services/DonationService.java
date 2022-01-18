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
import com.coding.LojoFundrasing.Models.User;
import com.coding.LojoFundrasing.Repos.DonationRepo;


@Service
public class DonationService {
	@Autowired
	private DonationRepo donrepo;
	@Autowired 
	private CommitteeService cservice;
	@Autowired
	private DonorService dservice;
	
	public Donation createDonation(Donation d) {
		/*List<Donation> recurrencedated = donrepo.findbyActBlueIdandCommittee_idandDate(d.getActBlueId(), d.getCommittee().getId(), d.getDondate(), d.getDonor().getId());
		System.out.println("donation id: " + d.getId());
		System.out.println("recurrence id: " + recurrencedated.getId());
		if (recurrencedated.size() > 0) {
				System.out.println("d recurring number: " + d.getId());
				//System.out.println("recurrence recurring number: " + recurrencedated.getId());
				for (int j = 0; j < recurrencedated.size(); j++) {
					System.out.println("same donation found but not recurring");
					recurrencedated.get(j).setAmount(d.getAmount());
					recurrencedated.get(j).setDonation_uploader(d.getDonation_uploader());
					recurrencedated.get(j).setEmailDonation(d.getEmailDonation());
					recurrencedated.get(j).setRecurring(d.getRecurring());
					recurrencedated.get(j).setRecurrenceNumber(d.getRecurrenceNumber());
					recurrencedated.get(j).setDonationRefcode1(d.getDonationRefcode1());
					recurrencedated.get(j).setDonationRefcode2(d.getDonationRefcode2());
					recurrencedated.get(j).setAmount(d.getAmount());
					recurrencedated.get(j).setDondate(d.getDondate());
					recurrencedated.get(j).setCommittee(d.getCommittee());
					return donrepo.save(d);
				}
		}*/
		d = findandSetDonation(d.getActBlueId(), d.getRecurring(), d.getRecurrenceNumber(), d.getDondate(), 
				d.getCommittee(), d.getDonor(), d.getAmount(), d.getDonation_uploader(), d.getEmailDonation(), 
				d.getDonationRefcode1(), d.getDonationRefcode2());	
		return donrepo.save(d);
	}
	
	public Donation createDonationfromUpload(String ActBlueId, String Recurring, Integer Recurrence, Date dateValue, 
			Committees committee, Donor donor, Double amount, User uploader, Emails email, String refcode1, 
			String refcode2) {
		Donation d = findandSetDonation(ActBlueId, Recurring, Recurrence, dateValue, 
				committee, donor, amount, uploader, email, refcode1, 
				refcode2);	
		return donrepo.save(d);
	}
	
	public Donation findandSetDonation(String ActBlueId, String Recurring, Integer Recurrence, Date dateValue, 
			Committees committee, Donor donor, Double amount, User uploader, Emails email, String refcode1, 
			String refcode2) {
		System.out.println("committee check " + donrepo.donationCountwithCOMMITTEE(committee.getId()));
		System.out.println("ID " + donor);
		List<Donation> recurrencedated = donrepo.findbyActBlueIdandCommittee_idandDate(ActBlueId, committee.getId(), dateValue, donor.getId());
		System.out.println("number of recurrences " + recurrencedated.size());
		System.out.println("OneRecurrencedated " + recurrencedated);
		Donation donation = null;
		List<Donation> donations;
		List<Donation> DonorDonations;
		//System.out.println("donation id: " + d.getId());
		//System.out.println("recurrence id: " + recurrencedated.getId());
		if (recurrencedated == null || recurrencedated.size() == 0) {
			System.out.println("0 donation with same date, committee, ABid and donor " + recurrencedated.size());
			donation = new Donation ();
			donation.setAmount(amount);
			donation.setDonation_uploader(uploader);
			donation.setEmailDonation(email);
			donation.setRecurring(Recurring);
			donation.setRecurrenceNumber(Recurrence);
			donation.setDonationRefcode1(refcode1);
			donation.setDonationRefcode2(refcode2);
			donation.setAmount(amount);
			donation.setDondate(dateValue);
			donation.setCommittee(committee);
			donation.setActBlueId(ActBlueId);
			donation.setDonor(donor);
			donation.setEmailDonation(email);
			donrepo.save(donation);
			donations = committee.getDonations();
			donations.add(donation);
			committee.setDonations(donations);
			cservice.createCommittee(committee);
			DonorDonations = donor.getContributions();
			DonorDonations.add(donation);
			donor.setContributions(DonorDonations);
			dservice.updateDonor(donor);
			return donation;
		}
		else {
				System.out.println("more than 0 donation with same date, committee, ABid and donor " + recurrencedated.size());
				for (int j = 0; j < recurrencedated.size(); j++) {
					System.out.println("same donation found but not recurring");
					recurrencedated.get(j).setAmount(amount);
					recurrencedated.get(j).setDonation_uploader(uploader);
					recurrencedated.get(j).setEmailDonation(email);
					recurrencedated.get(j).setRecurring(Recurring);
					recurrencedated.get(j).setRecurrenceNumber(Recurrence);
					recurrencedated.get(j).setDonationRefcode1(refcode1);
					recurrencedated.get(j).setDonationRefcode2(refcode2);
					recurrencedated.get(j).setAmount(amount);
					recurrencedated.get(j).setDondate(dateValue);
					recurrencedated.get(j).setCommittee(committee);
					donrepo.save(recurrencedated.get(j));
				}
				donation = recurrencedated.get(0);
				return (donation);
		}
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
