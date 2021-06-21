package com.coding.LojoFundrasing.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Data;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Repos.DonationRepo;
import com.coding.LojoFundrasing.Repos.EmailGroupRepo;

@Service
public class EmailGroupService {
	@Autowired
	private EmailGroupRepo egrepo;
	
	@Autowired
	private DonationRepo donrepo;
	
	public EmailGroup createEmailGroup(EmailGroup emailgroup) {
		return egrepo.save(emailgroup);
	}
	public EmailGroup getEmailGroupData(EmailGroup emailgroup, Long committee_id) {
		Long id = emailgroup.getId();
		//email performance
		Long groupOpeners = (long) 0;
		Long groupRecipients = (long) 0;
		Long groupClicks = (long) 0;
		Long groupBounces = (long) 0;
		Long groupUnsubscribers = (long) 0;
		//donation info
		Double groupaverage = 0.0;
		Double groupsum = 0.0;
		Integer groupdonationcount = 0;
		Integer groupdonorcount = 0;
		//recurring
		Integer groupRecurringDonorCount = 0;
		Integer groupRecurringDonationCount = 0;
		Double groupRecurringRevenue = 0.0;
		//rates
		Double groupunsubscribeRate = 0.0;
		Double groupclickRate = 0.0;
		Double groupopenRate = 0.0;
		Double groupbounceRate = 0.0;
		Double groupdonationsOpens = 0.0;
		Double groupdonationsClicks = 0.0;
		Double groupclicksOpens = 0.0;
		Double groupdonorsOpens = 0.0;
		Double groupdonorsClicks = 0.0;
			for (int i = 0; i < emailgroup.getEmails().size(); i++) {
					Emails email = emailgroup.getEmails().get(i);
					System.out.println(email.getEmailName());
					//calculate email performance info
					//need if these aren't null statement
					System.out.println(groupOpeners);
					System.out.println(email.getOpeners());
					groupOpeners = groupOpeners + email.getOpeners();
					System.out.println(groupOpeners);
					groupRecipients = groupRecipients + email.getRecipients();
					System.out.println(groupRecipients);
					groupClicks = groupClicks + email.getClicks();
					System.out.println(groupClicks);
					groupBounces = groupBounces + email.getBounces();
					System.out.println(groupBounces);
					groupUnsubscribers = groupUnsubscribers + email.getUnsubscribers();
					System.out.println(groupUnsubscribers);
					//set email performance info
					emailgroup.setGroupOpeners(groupOpeners);
					emailgroup.setGroupRecipients(groupRecipients);
					emailgroup.setGroupClicks(groupClicks);
					emailgroup.setGroupBounces(groupBounces);
					emailgroup.setGroupUnsubscribers(groupUnsubscribers);
					//calculate donation info
					groupsum = groupsum + email.getEmaildata().getEmailsum(); 
					System.out.println(groupsum);
					groupdonationcount = groupdonationcount + email.getEmaildata().getDonationcount();
					System.out.println(groupdonationcount);
					//set donation info
					emailgroup.setGroupsum(groupsum);
					emailgroup.setGroupdonationcount(groupdonationcount);
					//calculate recurring info
					//set an if statement if this exists
					groupRecurringDonationCount = groupRecurringDonationCount + email.getRecurringDonationCount();
					System.out.println(groupRecurringDonationCount);
					groupRecurringRevenue = groupRecurringRevenue + email.getRecurringRevenue();
					System.out.println(groupRecurringRevenue);
					//set recurring info
					//fix donor count to only count unique
					emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
					emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
					/*eaverage = erepo.averages(id, committee_id);
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
					//set recurring functions
					email.setRecurringDonorCount(recurringDonorCount);
					email.setRecurringDonationCount(recurringDonationCount);
					email.setRecurringRevenue(recurringRevenue);
					erepo.save(email);
					return datarepo.save(emaildata);*/
		}
		if (emailgroup.getGroupBounces() != null) {
			//average
			groupaverage = groupsum/emailgroup.getGroupdonationcount();
			emailgroup.setGroupaverage(groupaverage);
			System.out.println(groupaverage);
			//donor count
			System.out.println("groupdonorcount");
			groupdonorcount = donrepo.findDonorsinGroup(committee_id, id);
			emailgroup.setGroupdonorcount(groupdonorcount);
			System.out.println(groupdonorcount);
			//recurring donor count
			groupRecurringDonorCount = donrepo.findRecurringDonorsinGroup(committee_id, id);
			emailgroup.setGroupRecurringDonorCount(groupRecurringDonorCount);
			System.out.println(groupRecurringDonorCount);
			//variables for aggregate functions
			Double unsubs = (double) emailgroup.getGroupUnsubscribers();
			Double receps = (double) emailgroup.getGroupRecipients();
			Double clicks = (double) emailgroup.getGroupClicks();
			Double opens = (double) emailgroup.getGroupOpeners();
			Double bounces = (double) emailgroup.getGroupBounces();
			//functions
			groupunsubscribeRate = unsubs/receps;
			System.out.println("groupunsubscribeRate " + groupunsubscribeRate);
			groupopenRate = opens/receps;
			System.out.println("groupopenRate " + groupopenRate);
			groupopenRate= clicks/receps;
			System.out.println("groupopenRate " + groupopenRate);
			groupbounceRate = bounces/receps;
			System.out.println("groupbounceRate " + groupbounceRate);
			groupclicksOpens = clicks/opens;
			System.out.println("groupclicksOpens) " + groupclicksOpens);
			groupdonationsOpens = groupdonationcount/opens;
			System.out.println("groupdonationsOpens) " + groupdonationsOpens);
			groupdonationsClicks = groupdonationcount/clicks;
			System.out.println("groupdonationsClicks " + groupdonationsClicks);
			groupdonorsOpens = groupdonorcount/opens;
			System.out.println("groupdonorsOpens " + groupdonorsOpens);
			groupdonorsClicks = groupdonorcount/clicks;
			System.out.println("groupdonorsClicks " + groupdonorsClicks);
		}
		return egrepo.save(emailgroup);
	}
}
