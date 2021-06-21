package com.coding.LojoFundrasing.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Data;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Repos.EmailGroupRepo;

@Service
public class EmailGroupService {
	@Autowired
	private EmailGroupRepo egrepo;
	
	public EmailGroup createEmailGroup(EmailGroup emailgroup) {
		return egrepo.save(emailgroup);
	}
	public void getEmailGroupData(EmailGroup emailgroup, Long committee_id) {
		Long id = emailgroup.getId();
		//email performance
		Long groupOpeners = null;
		Long groupRecipients = null;
		Long groupClicks = null;
		Long groupBounces = null;
		Long groupUnsubscribers = null;
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
					groupsum = groupsum + email.getEmaildata().getEmailsum(); 
					System.out.println(groupsum);
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
	}
}
