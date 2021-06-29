package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
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
					if (email.getOpeners() != null) {
						//calculate email performance info
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
						if (email.getEmaildata().getEmailsum() != null) {
							//calculate donation info
							groupsum = groupsum + email.getEmaildata().getEmailsum(); 
							System.out.println("group sum: " + groupsum);
							groupdonationcount = groupdonationcount + email.getEmaildata().getDonationcount();
							System.out.println("group donation count: " + groupdonationcount);
							//set donation info
							emailgroup.setGroupsum(groupsum);
							emailgroup.setGroupdonationcount(groupdonationcount);
							if (email.getRecurringDonationCount() != null) {
								//calculate recurring info
								groupRecurringDonationCount = groupRecurringDonationCount + email.getRecurringDonationCount();
								System.out.println(groupRecurringDonationCount);
								if (email.getRecurringRevenue() != null) {
									groupRecurringRevenue = groupRecurringRevenue + email.getRecurringRevenue();
									System.out.println("r revenue" + groupRecurringRevenue);
								}
								else {
									groupRecurringRevenue = groupRecurringRevenue + 0.0;
								}
								//set recurring info
								emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
								emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
							}
						}
						else {
							//set donation info
							emailgroup.setGroupsum(groupsum);
							emailgroup.setGroupdonationcount(groupdonationcount);
							//set recurring info
							emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
							emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
						}
					}
					else {
						//set email performance info
						emailgroup.setGroupOpeners(groupOpeners);
						emailgroup.setGroupRecipients(groupRecipients);
						emailgroup.setGroupClicks(groupClicks);
						emailgroup.setGroupBounces(groupBounces);
						emailgroup.setGroupUnsubscribers(groupUnsubscribers);
						if (email.getEmaildata().getEmailsum() != null) {
							//calculate donation info
							groupsum = groupsum + email.getEmaildata().getEmailsum(); 
							System.out.println(groupsum);
							groupdonationcount = groupdonationcount + email.getEmaildata().getDonationcount();
							System.out.println(groupdonationcount);
							//set donation info
							emailgroup.setGroupsum(groupsum);
							emailgroup.setGroupdonationcount(groupdonationcount);
							if (email.getRecurringDonationCount() != null) {
								//calculate recurring info
								groupRecurringDonationCount = groupRecurringDonationCount + email.getRecurringDonationCount();
								System.out.println(groupRecurringDonationCount);
								if (email.getRecurringRevenue() != null) {
									groupRecurringRevenue = groupRecurringRevenue + email.getRecurringRevenue();
									System.out.println("r revenue" + groupRecurringRevenue);
								}
								else {
									groupRecurringRevenue = groupRecurringRevenue + 0.0;
								}
								//set recurring info
								emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
								emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
							}
						}
						else {
							//set donation info
							emailgroup.setGroupsum(groupsum);
							emailgroup.setGroupdonationcount(groupdonationcount);
							//set recurring info
							emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
							emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
						}
					}
		}
		if (emailgroup.getGroupdonationcount() != 0) {
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
			//rate functions 
			//variables for rate functions
			Double unsubs = 0.0;
			Double receps = 0.0;
			Double clicks = 0.0;
			Double opens = 0.0;
			Double bounces = 0.0;
			if (emailgroup.getGroupRecipients() != 0) {
				System.out.println("recipients");
				receps = (double) emailgroup.getGroupRecipients();
				System.out.println("recipients: " + receps);
				if (emailgroup.getGroupOpeners() != 0) {
					//open rate
					opens = (double) emailgroup.getGroupOpeners();
					groupopenRate = opens/receps;
					System.out.println("groupopenRate " + groupopenRate);
					emailgroup.setGroupopenRate(groupopenRate);
					//donations/opens
					groupdonationsOpens = groupdonationcount/opens;
					System.out.println("groupdonationsOpens " + groupdonationsOpens);
					emailgroup.setGroupdonationsOpens(groupdonationsOpens);
					//donors/opens
					groupdonorsOpens = groupdonorcount/opens;
					System.out.println("groupdonorsOpens " + groupdonorsOpens);
					emailgroup.setGroupdonorsOpens(groupdonorsOpens);
					if (emailgroup.getGroupClicks() != 0) {
						//click rate
						clicks = (double) emailgroup.getGroupClicks();
						groupclickRate= clicks/receps;
						System.out.println("groupclickRate " + groupclickRate);
						emailgroup.setGroupclickRate(groupclickRate);
						//clicks/opens
						groupclicksOpens = clicks/opens;
						System.out.println("groupclicksOpens " + groupclicksOpens);
						emailgroup.setGroupclicksOpens(groupclicksOpens);
						//donations/clicks
						groupdonationsClicks = groupdonationcount/clicks;
						System.out.println("groupdonationsClicks " + groupdonationsClicks);
						emailgroup.setGroupdonationsClicks(groupdonationsClicks);
						//donors/clicks
						groupdonorsClicks = groupdonorcount/clicks;
						System.out.println("groupdonorsClicks " + groupdonorsClicks);
						emailgroup.setGroupdonorsClicks(groupdonorsClicks);
						if (emailgroup.getGroupUnsubscribers() != 0) {
							//unsubscribe rate
							unsubs = (double) emailgroup.getGroupUnsubscribers();
							groupunsubscribeRate = unsubs/receps;
							System.out.println("groupunsubscribeRate " + groupunsubscribeRate);
							emailgroup.setGroupunsubscribeRate(groupunsubscribeRate);
							if (emailgroup.getGroupBounces() != 0) {
								//bounce rate
								bounces = (double) emailgroup.getGroupBounces();
								groupbounceRate = bounces/receps;
								System.out.println("groupbounceRate " + groupbounceRate);
								emailgroup.setGroupbounceRate(groupbounceRate);
							}
						}
					}
				}
			}
			else {
				emailgroup.setGroupopenRate(groupopenRate);
				emailgroup.setGroupdonationsOpens(groupdonationsOpens);
				emailgroup.setGroupdonorsOpens(groupdonorsOpens);
				emailgroup.setGroupclickRate(groupclickRate);
				emailgroup.setGroupclicksOpens(groupclicksOpens);
				emailgroup.setGroupdonationsClicks(groupdonationsClicks);
				emailgroup.setGroupdonorsClicks(groupdonorsClicks);
				emailgroup.setGroupunsubscribeRate(groupunsubscribeRate);
				emailgroup.setGroupbounceRate(groupbounceRate);
			}
		}
		else {
			emailgroup.setGroupaverage(groupaverage);
			emailgroup.setGroupdonorcount(groupdonorcount);
			emailgroup.setGroupRecurringDonorCount(groupRecurringDonorCount);
			emailgroup.setGroupopenRate(groupopenRate);
			emailgroup.setGroupdonationsOpens(groupdonationsOpens);
			emailgroup.setGroupdonorsOpens(groupdonorsOpens);
			emailgroup.setGroupclickRate(groupclickRate);
			emailgroup.setGroupclicksOpens(groupclicksOpens);
			emailgroup.setGroupdonationsClicks(groupdonationsClicks);
			emailgroup.setGroupdonorsClicks(groupdonorsClicks);
			emailgroup.setGroupunsubscribeRate(groupunsubscribeRate);
			emailgroup.setGroupbounceRate(groupbounceRate);
		}
		return egrepo.save(emailgroup);
	}
	public List<EmailGroup> EmailGroupList(@Param("startdateE") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdateE, @Param("enddateE") 
	@DateTimeFormat(pattern ="yyyy-MM-dd") String enddateE, Long committee_id){
		List <EmailGroup> emails = new ArrayList <EmailGroup>();
		System.out.println("made it to service ");
		List<Long> Ids = egrepo.findGroupByOrderByDesc(startdateE, enddateE, committee_id);
		System.out.println("ids size " + Ids.size());
		for (int i = 0; i < Ids.size(); i++) {
			emails.add(egrepo.findbyIdandCommittee(Ids.get(i), committee_id));
		}
		System.out.println("emails size " + emails.size());
		return emails;
	}
}
