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
	public Data getEmailGroupData(EmailGroup emailgroup, Long committee_id) {
		Data emaildata = email.getEmaildata();
		String refcode = email.getEmailRefcode();
		System.out.println("refcode: " + refcode);
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
					//set recurring functions
					email.setRecurringDonorCount(recurringDonorCount);
					email.setRecurringDonationCount(recurringDonationCount);
					email.setRecurringRevenue(recurringRevenue);
					erepo.save(email);
					return datarepo.save(emaildata);
				}
			return datarepo.save(emaildata);
		}
	}
}
