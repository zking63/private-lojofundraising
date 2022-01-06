package com.coding.LojoFundrasing.Services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Data;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.DonorData;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Repos.DataRepo;
import com.coding.LojoFundrasing.Repos.DonationRepo;
import com.coding.LojoFundrasing.Repos.EmailRepo;

@Service
public class EmailService {
	@Autowired
	private EmailRepo erepo;
	
	@Autowired
	private DataRepo datarepo;
	
	@Autowired
	private DonationRepo drepo;
	
	@Autowired
	private EmailGroupService egservice;
	
	public Emails createEmail(Emails email) {
		return erepo.save(email);
	}
	
	public Emails updateEmail(Emails email) {
		return erepo.save(email);
	}
	
	public List<Emails> allEmails(){
		return erepo.findAll();
	}
	
	public Emails findEmailbyId(long id) {
		return erepo.findById(id).orElse(null);
	}
	
	public List<Emails> findEmailswithoutGroup(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, 
			@Param("enddateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findemailswithoutGroup(startdateE, enddateE, committee_id);
	}
	
	public Emails findEmailbyRefcode(String emailRefcode) {
		return erepo.findByemailRefcode(emailRefcode);
	}
	public Emails findEmailbyRefcodeandCommittee(String emailRefcode, String emailRefcode2, Committees committee) {
		Long committee_id = committee.getId();
		return erepo.findByemailRefcodeandCommittee(emailRefcode, emailRefcode2, committee_id);
	}
	public void delete(long id) {
		erepo.deleteById(id);
	}
	public List<Donation> getEmailDonations(Emails email){
		Long id = email.getId();
		return drepo.findByemailDonation(id);
	}
	public Data getEmailData(Emails email, Long committee_id) {
		//need to make emaildata find by email id OR add email data to email
		Data emaildata = datarepo.findEmailData(email.getId());
		if (emaildata != null) {
			System.out.println("email data: " + emaildata.getId());
		}
		//String refcode = email.getEmailRefcode();
		//System.out.println("refcode: " + refcode);
		Long id = email.getId();
		Double esum = 0.00;
		Double eaverage = 0.00;
		Integer donationscount = 0;
		Integer donorscount = 0;
		Integer recurringDonorCount = 0;
		Integer recurringDonationCount = 0;
		Double recurringRevenue = 0.00;
		Double unsubscribeRate = 0.00;
		Double clickRate = 0.00;
		Double openRate = 0.00;
		Double bounceRate = 0.00;
		Double donationsOpens = 0.00;
		Double donationsClicks = 0.00;
		Double donorsOpens = 0.00;
		Double donorsClicks = 0.00;
		Double clicksOpens = 0.00;
		List<Data> alldata = datarepo.findAll();
		if (emaildata == null) {
			esum = erepo.sums(id, committee_id);
			System.out.println("esum:" + esum);
			eaverage = erepo.averages(id, committee_id);
			donationscount = erepo.donationscount(id, committee_id);
			donorscount = erepo.donorscount(id, committee_id);
			//aggregate functions
			if (email.getBounces() != null) {
				//variables for aggregate functions
				Double unsubs = (double) email.getUnsubscribers();
				Double receps = (double) email.getRecipients();
				Double clicks = (double) email.getClicks();
				Double opens = (double) email.getOpeners();
				Double bounces = (double) email.getBounces();
				//functions
				unsubscribeRate = unsubs/receps;
				openRate = opens/receps;
				clickRate = clicks/receps;
				bounceRate = bounces/receps;
				clicksOpens = clicks/opens;
				donationsOpens = donationscount/opens;
				donationsClicks = donationscount/clicks;
				donorsOpens = donorscount/opens;
				donorsClicks = donorscount/clicks;
			}
			//recurring functions
			recurringDonorCount = erepo.RecurringDonorCount(id, committee_id);
			recurringDonationCount = erepo.RecurringDonationCount(id, committee_id);
			recurringRevenue = erepo.RecurringDonationSum(id, committee_id);
			//set recurring functions
			if (recurringRevenue == null) {
				System.out.println("recurringRevenue: " + "null");
				recurringRevenue = 0.0;
			}
			else {
				System.out.println("recurringRevenue: " + recurringRevenue);
			}
			email.setRecurringDonorCount(recurringDonorCount);
			email.setRecurringDonationCount(recurringDonationCount);
			email.setRecurringRevenue(recurringRevenue);
			erepo.save(email);
			emaildata = new Data(eaverage, esum, donationscount, donorscount, unsubscribeRate, clickRate, 
					openRate, bounceRate, donationsOpens, donationsClicks, clicksOpens, donorsOpens, donorsClicks, email);
			datarepo.save(emaildata);
			if (email.getEmailgroup() != null) {
				egservice.getEmailGroupData(email.getEmailgroup(), committee_id);
			}
			return datarepo.save(emaildata);
		}
		else {
			for (int i = 0; i < alldata.size(); i++) {
				if (id == alldata.get(i).getDataEmail().getId()) {
					Long edid = emaildata.getId();
					edid = alldata.get(i).getId();
					emaildata = datarepo.findById(edid).orElse(null);
					esum = erepo.sums(id, committee_id);
					eaverage = erepo.averages(id, committee_id);
					donationscount = erepo.donationscount(id, committee_id);
					donorscount = erepo.donorscount(id, committee_id);
					emaildata.setEmailsum(esum);
					emaildata.setDonationcount(donationscount);
					emaildata.setDonorcount(donorscount);
					emaildata.setEmailAverage(eaverage);
					//aggregate functions
					if (email.getBounces() != null) {
						//variables for aggregate functions
						Double unsubs = (double) email.getUnsubscribers();
						Double receps = (double) email.getRecipients();
						Double clicks = (double) email.getClicks();
						Double opens = (double) email.getOpeners();
						Double bounces = (double) email.getBounces();
						//functions
						unsubscribeRate = unsubs/receps;
						openRate = opens/receps;
						clickRate = clicks/receps;
						bounceRate = bounces/receps;
						clicksOpens = clicks/opens;
						donationsOpens = donationscount/opens;
						donationsClicks = donationscount/clicks;
						donorsOpens = donorscount/opens;
						donorsClicks = donorscount/clicks;
					}
					//setting aggregate functions
					emaildata.setUnsubscribeRate(unsubscribeRate);
					emaildata.setOpenRate(openRate);
					emaildata.setClickRate(clickRate);
					emaildata.setBounceRate(bounceRate);
					emaildata.setClicksOpens(clicksOpens);
					emaildata.setDonationsOpens(donationsOpens);
					emaildata.setDonationsClicks(donationsClicks);
					emaildata.setDonorsOpens(donorsOpens);
					emaildata.setDonorsClicks(donorsClicks);
					//recurring functions
					recurringDonorCount = erepo.RecurringDonorCount(id, committee_id);
					recurringDonationCount = erepo.RecurringDonationCount(id, committee_id);
					recurringRevenue = erepo.RecurringDonationSum(id, committee_id);
					if (recurringRevenue == null) {
						System.out.println("recurringRevenue: " + "null");
						recurringRevenue  = 0.0;
					}
					else {
						System.out.println("recurringRevenue: " + recurringRevenue);
					}
					//set recurring functions
					email.setRecurringDonorCount(recurringDonorCount);
					email.setRecurringDonationCount(recurringDonationCount);
					email.setRecurringRevenue(recurringRevenue);
					erepo.save(email);
					datarepo.save(emaildata);
					if (email.getEmailgroup() != null) {
						egservice.getEmailGroupData(email.getEmailgroup(), committee_id);
					}
					return datarepo.save(emaildata);
				}
			}
			datarepo.save(emaildata);
			if (email.getEmailgroup() != null) {
				egservice.getEmailGroupData(email.getEmailgroup(), committee_id);
			}
			return datarepo.save(emaildata);
		}
	}
	//sorting
	//date/time
	public List<Emails> EmailTest(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByOrderByDesc(startdateE, enddateE, committee_id);
	}
	public List<Emails> EmailTestAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByOrderByAsc(startdateE, enddateE, committee_id);
	}
	//averages
	public List<Emails> AvDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByAverageOrderByDesc(startdateE, enddateE, committee_id);
	}
	public List<Emails> AverageAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByAverageOrderByAsc(startdateE, enddateE, committee_id);
	}
	//sums
	public List<Emails> SumDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findBySumOrderByDesc(startdateE, enddateE, committee_id);
	}
	public List<Emails> SumAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findBySumOrderByAsc(startdateE, enddateE, committee_id);
	}
	//donation count
	public List<Emails> DonationsCountDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByDonationsCountOrderByDesc(startdateE, enddateE, committee_id);
	}
	public List<Emails> DonationsCountAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByDonationsCountOrderByAsc(startdateE, enddateE, committee_id);
	}
	//donor count 
	public List<Emails> DonorCountDesc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByDonorCountOrderByDesc(startdateE, enddateE, committee_id);
	}
	public List<Emails> DonorCountAsc(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		return erepo.findByDonorCountOrderByAsc(startdateE, enddateE, committee_id);
	}
}
