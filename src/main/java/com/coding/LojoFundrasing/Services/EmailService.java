package com.coding.LojoFundrasing.Services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Committees;
//import com.coding.LojoFundrasing.Models.Data;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.DonorData;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.User;
//import com.coding.LojoFundrasing.Repos.DataRepo;
import com.coding.LojoFundrasing.Repos.DonationRepo;
import com.coding.LojoFundrasing.Repos.EmailRepo;

@Service
public class EmailService {
	@Autowired
	private EmailRepo erepo;
	
	/*@Autowired
	private DataRepo datarepo;*/
	
	@Autowired
	private DonationRepo drepo;
	
	@Autowired
	private EmailGroupService egservice;
	
	@Autowired
	private CommitteeService cservice;
	
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
		return erepo.findByemailRefcode1(emailRefcode);
	}
	public Emails findEmailbyRefcodeandCommittee(String emailRefcode, String emailRefcode2, Committees committee) {
		Long committee_id = committee.getId();
		return erepo.findByemailRefcodeandCommittee(emailRefcode, emailRefcode2, committee_id);
	}
	public Emails findEmailbyOneRefcodeandCommittee(String emailRefcode, Committees committee) {
		Long committee_id = committee.getId();
		return erepo.findByemailOneRefcodeandCommittee(emailRefcode, committee_id);
	}
	public Emails findEmailbyRefcodeTWOandCommittee(String emailRefcode2, Committees committee) {
		Long committee_id = committee.getId();
		return erepo.findByemailRefcodeTWOandCommittee(emailRefcode2, committee_id);
	}
	public void delete(long id) {
		erepo.deleteById(id);
	}
	public List<Donation> getEmailDonations(Emails email){
		Long id = email.getId();
		return drepo.findByemailDonation(id);
	}
	public Emails setEmailThroughDonation(String refcode, String refcode2, Committees committee, User uploader) throws ParseException {
		Emails email = null;
		Boolean refcodesFiled = false;
		Boolean committeeSetList = false;
		
		while (refcodesFiled == false) {
			if (refcode2 == null || refcode2.isEmpty() || refcode2 == " ") {
				if (refcode == null || refcode.isEmpty() || refcode == " ") {
					System.out.println("no refcodes");
					//make a find by name/date with null refcodes
					refcodesFiled = true;
				}
				else {
					System.out.println("refcode2 == null && refcode != null");
					email = findEmailbyOneRefcodeandCommittee(refcode, committee);
					refcodesFiled = true;
				}
			}
			else {
				if (refcode == null || refcode.isEmpty() || refcode == " ") {
					System.out.println("refcode2 != null && refcode == null");
					email = findEmailbyRefcodeTWOandCommittee(refcode2, committee);
					refcodesFiled = true;
				}
				else {
					System.out.println("refcode2 != null && refcode != null");
					email = findEmailbyRefcodeandCommittee(refcode, refcode2, committee);
					refcodesFiled = true;
				}
			}
		}
		if (email == null) {
    		String undate1 = "0001-01-01 01:01";
    		Date undate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(undate1);
    		email = new Emails();
        	email.setEmailName(null);
        	System.out.println("TEMP DATE: " + undate);
        	email.setEmaildate(undate);
        	email.setEmailRefcode1(refcode);
        	email.setEmailRefcode2(refcode2);
        	email.setBounces(null);
        	email.setClicks(null);
        	email.setOpeners(null);
        	email.setRecipients(null);
        	email.setUnsubscribers(null);
        	email.setExcludedList(null);
        	email.setList(null);
        	email.setSender(null);
        	email.setSubjectLine(null);
        	email.setEmailCategory(null);
        	email.setTesting(null);
        	email.setVariant(null);
        	email.setLink(null);
        	email.setEmail_uploader(uploader);
        	createEmail(email);
        	while (committeeSetList == false) {
    			if (committee.getEmails() == null || committee.getEmails().size() == 0) {
    				email.setCommittee(committee);
    				List<Emails> emailCommittee = new ArrayList<Emails>();
    				emailCommittee.add(email);
    				committee.setEmails(emailCommittee);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
    			else {
    				email.setCommittee(committee);
    				List<Emails> emailCommittee = committee.getEmails();
    				emailCommittee.add(email);
    				committee.setEmails(emailCommittee);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
        	}
        	updateEmail(email);
        	String tempname = "ID: " + email.getId();
        	email.setEmailName(tempname);
        	System.out.println("TEMP NAME: " + tempname);
        	updateEmail(email);
        	return email;
		}
		else {
        	System.out.println("found email: " + email.getId() + ", " + email.getEmailName());
			return email;
		}
	}
	
	public void setUpEmailsfromUpload(String recipientList, String excludedList, Long openers, Long bounces, Long unsubscribers, 
			Long clicks, Long recipients, User uploader, String nameValue, String refcode, String refcode2,  
			Date date, Committees committee, String sender, String subject, String category, 
			String testing, String variant, String link, Integer rowNumber) {
		System.out.println("email set up found");
		System.out.println("*****NAME " + nameValue);
		System.out.println("*****refcode " + refcode);
		System.out.println("*****refcode2 " + refcode2);
		Emails email = null;
		Boolean refcodesFiled = false;
		Boolean committeeSetList = false;
		Boolean variantSet = false;
		List<Emails> emails = null;
		
		if (nameValue.isEmpty() || nameValue == null || date == null) {
			rowNumber = rowNumber +1;
			System.out.println("*****NAME " + nameValue);
			System.out.println("*****DATE " + date);
			System.out.println("*****NOTHING IN THIS ROW " + rowNumber);
			return;
		}
		while (refcodesFiled == false) {
			if (refcode2 == null || refcode2.isEmpty() || refcode2 == " ") {
				if (refcode == null || refcode.isEmpty() || refcode == " ") {
					System.out.println("no refcodes");
					//make a find by name/date with null refcodes
					refcodesFiled = true;
				}
				else {
					System.out.println("refcode2 == null && refcode != null");
					email = findEmailbyOneRefcodeandCommittee(refcode, committee);
					refcodesFiled = true;
				}
			}
			else {
				if (refcode == null || refcode.isEmpty() || refcode == " ") {
					System.out.println("refcode2 != null && refcode == null");
					email = findEmailbyRefcodeTWOandCommittee(refcode2, committee);
					refcodesFiled = true;
				}
				else {
					System.out.println("refcode2 != null && refcode != null");
					email = findEmailbyRefcodeandCommittee(refcode, refcode2, committee);
					refcodesFiled = true;
				}
			}
		}
		System.out.println("                                          TESTING: " + testing);
		while (variantSet == false) {
			String test = testing.toUpperCase();
			if (test.contains("SENDER")) {
				System.out.println("                                          TESTING = sender: " + testing);
				variant = sender;
				variantSet = true;
			}
			else if (test.contains("SUBJECT")) {
				System.out.println("                                          TESTING = subject: " + testing);
				variant = subject;
				variantSet = true;
			}
			else {
				System.out.println("                                          TESTING = other: " + testing);
				variant = variant;
				variantSet = true;
			}
		}
		if (email == null) {
			System.out.println("*****email not found ");
        	email = new Emails();
        	email.setEmailName(nameValue);
        	email.setEmaildate(date);
        	email.setEmailRefcode1(refcode);
        	email.setEmailRefcode2(refcode2);
        	email.setBounces(bounces);
        	email.setClicks(clicks);
        	email.setOpeners(openers);
        	email.setRecipients(recipients);
        	email.setUnsubscribers(unsubscribers);
        	email.setExcludedList(excludedList);
        	email.setList(recipientList);
        	email.setEmail_uploader(uploader);
        	email.setSender(sender);
        	email.setSubjectLine(subject);
        	email.setEmailCategory(category.toUpperCase());
        	email.setTesting(testing.toUpperCase());
        	email.setVariant(variant);
        	email.setLink(link);
        	createEmail(email);
        	while (committeeSetList == false) {
    			if (committee.getBigtest() == null || committee.getEmails().size() == 0) {
    				email.setCommittee(committee);
    				List<Emails> emailCommittee = new ArrayList<Emails>();
    				emailCommittee.add(email);
    				committee.setEmails(emailCommittee);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
    			else {
    				email.setCommittee(committee);
    				List<Emails> emailCommittee = committee.getEmails();
    				emailCommittee.add(email);
    				committee.setEmails(emailCommittee);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
        	}
        	updateEmail(email);
    		//getEmailData(email, committee.getId());
    		CalculateEmailData(email, committee.getId());
			System.out.println("NEW Id: " + email.getId() + " Email: " + email.getEmailName());
			return;
		}
		if (email !=  null) {
        	System.out.println("found email: " + email.getId() + ", " + email.getEmailName());
        	email.setEmailName(nameValue);
        	email.setEmaildate(date);
        	email.setEmailRefcode1(refcode);
        	email.setEmailRefcode2(refcode2);
        	email.setBounces(bounces);
        	email.setClicks(clicks);
        	email.setOpeners(openers);
        	email.setRecipients(recipients);
        	email.setUnsubscribers(unsubscribers);
        	email.setExcludedList(excludedList);
        	email.setList(recipientList);
        	email.setEmail_uploader(uploader);
        	email.setSender(sender);
        	email.setSubjectLine(subject);
        	email.setEmailCategory(category);
        	email.setTesting(testing);
        	email.setVariant(variant);
        	email.setLink(link);
        	updateEmail(email);
    		CalculateEmailData(email, committee.getId());
			System.out.println("Id: " + email.getId() + " Email: " + email.getEmailName());
			return;
		}
	}
	
	public void CalculateEmailData(Emails email, Long committee_id) {
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
		
			esum = erepo.sums(id, committee_id);
			System.out.println("esum in calculate:" + esum);
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
				System.out.println("unsubscribeRate: " + unsubscribeRate);
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
			email.setEmaildonationaverage(eaverage);
			email.setEmaildonationsum(esum);
			email.setRecurringDonationCount(recurringDonationCount);
			email.setRecurringDonorCount(recurringDonorCount);
			email.setRecurringRevenue(recurringRevenue);
			email.setEmaildonationcount(donationscount);
			email.setEmaildonorcount(donorscount);
			email.setEmailunsubscribeRate(unsubscribeRate);
			System.out.println("unsubscribeRate set: " + email.getEmailunsubscribeRate());
			email.setEmailclickRate(clickRate);
			email.setEmailopenRate(openRate);
			email.setBounceRate(bounceRate);
			email.setEmaildonationsOpens(donationsOpens);
			email.setEmaildonationsClicks(donationsClicks);
			email.setEmailclicksOpens(clicksOpens);
			email.setEmaildonorsOpens(donorsOpens);
			email.setEmaildonorsClicks(donorsClicks);
			erepo.save(email);
    		if (email.getEmailgroup() != null) {
    			egservice.getEmailGroupData(email.getEmailgroup().getId(), committee_id);
    		}
	}
	/*public Data getEmailData(Emails email, Long committee_id) {
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
					System.out.println("find esum: ");
					esum = erepo.sums(id, committee_id);
					System.out.println("after find esum: ");
					eaverage = erepo.averages(id, committee_id);
					System.out.println("don count: ");
					donationscount = erepo.donationscount(id, committee_id);
					System.out.println("after don count: ");
					donorscount = erepo.donorscount(id, committee_id);
					System.out.println("after donors count: ");
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
	}*/
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
