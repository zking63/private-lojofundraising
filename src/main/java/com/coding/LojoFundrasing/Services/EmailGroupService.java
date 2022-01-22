package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

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
	public EmailGroup findEmailGroupbyName(String emailGroupname, Long committee_id) {
		EmailGroup group = egrepo.findbyNameandCommittee(emailGroupname, committee_id);
		return group;
	}
	public EmailGroup findEmailGroupbyId(Long id, Long committee_id) {
		EmailGroup group = egrepo.findbyIdandCommittee(id, committee_id);
		return group;
	}
	public void getEmailGroupData(Long emailGroupId, Long committee_id) {
		EmailGroup emailgroup = egrepo.findbyIdandCommittee(emailGroupId, committee_id);
		System.out.println(emailgroup.getEmailgroupName());
		//email performance
		Long groupOpeners = egrepo.GroupOpeners(emailGroupId, committee_id);
		Long groupRecipients = egrepo.GroupRecipients(emailGroupId, committee_id);
		Long groupClicks = egrepo.GroupClicks(emailGroupId, committee_id);
		Long groupBounces = egrepo.GroupBounces(emailGroupId, committee_id);
		Long groupUnsubscribers = egrepo.GroupUnsubscribers(emailGroupId, committee_id);
		//donation info
		Double groupaverage = egrepo.GroupAveragePerDonation(emailGroupId, committee_id);
		Double groupsum = egrepo.GroupRevenue(emailGroupId, committee_id);
		Integer groupdonationcount = egrepo.GroupDonations(emailGroupId, committee_id);
		Integer groupdonorcount = egrepo.GroupDonors(emailGroupId, committee_id);
		//recurring
		Integer groupRecurringDonorCount = egrepo.GroupRecurringDonors(emailGroupId, committee_id);
		Integer groupRecurringDonationCount = egrepo.GroupRecurringDonations(emailGroupId, committee_id);
		Double groupRecurringRevenue = egrepo.GroupRecurringRevenue(emailGroupId, committee_id);
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
		//strings
		String test = null;
		String category = null;
		
		emailgroup.setGroupOpeners(groupOpeners);
		emailgroup.setGroupRecipients(groupRecipients);
		emailgroup.setGroupClicks(groupClicks);
		emailgroup.setGroupBounces(groupBounces);
		emailgroup.setGroupUnsubscribers(groupUnsubscribers);
		
		emailgroup.setGroupsum(groupsum);
		emailgroup.setGroupdonationcount(groupdonationcount);
		emailgroup.setGroupRecurringDonationCount(groupRecurringDonationCount);
		emailgroup.setGroupRecurringRevenue(groupRecurringRevenue);
		emailgroup.setGroupaverage(groupaverage);
		emailgroup.setGroupdonorcount(groupdonorcount);
		emailgroup.setGroupRecurringDonorCount(groupRecurringDonorCount);
		
		//calculate email performance + fundraising stats
		if (groupOpeners != null && groupOpeners != 0) {
			System.out.println("groupOpeners is 0 " + groupOpeners);
			groupclicksOpens = (double) groupClicks/groupOpeners;
			System.out.println("groupclicksOpens " + groupclicksOpens);
			groupdonationsOpens = (double) groupdonationcount/groupOpeners;
			System.out.println("groupdonationsOpens " + groupdonationsOpens);
			groupdonorsOpens = (double) groupdonorcount/groupOpeners;
			System.out.println("groupdonorsOpens " +  groupdonorsOpens);
		}
		if (groupClicks != null && groupClicks != 0) {
			System.out.println("groupClicks is NOT 0 " + groupClicks);
			System.out.println("groupdonationcount " + groupdonationcount);
			groupdonationsClicks = (double) groupdonationcount/groupClicks;
			System.out.println("groupdonationsClicks " + groupdonationsClicks);
			groupdonorsClicks = (double) groupdonorcount/groupClicks;
			System.out.println("groupdonorcount " + groupdonorcount);
			System.out.println("groupdonorsClicks " + groupdonorsClicks);
		}
		
		//calculate email performance stats
		if (groupRecipients != null && groupRecipients != 0) {
			System.out.println("groupRecipients is 0 " + groupRecipients);
			if (groupUnsubscribers != null && groupUnsubscribers != 0) {
				groupunsubscribeRate = (double) groupUnsubscribers/groupRecipients;
			}
			if (groupBounces != null && groupBounces != 0) {
				groupbounceRate = (double) groupBounces/groupRecipients;
			}
			if (groupClicks != null && groupClicks != 0) {
				groupclickRate = (double) groupClicks/groupRecipients;
			}
			if (groupOpeners != null && groupOpeners != 0) {
				groupopenRate = (double) groupOpeners/groupRecipients;
			}
		}
		emailgroup.setGroupopenRate(groupopenRate);
		emailgroup.setGroupdonationsOpens(groupdonationsOpens);
		emailgroup.setGroupdonorsOpens(groupdonorsOpens);
		emailgroup.setGroupclickRate(groupclickRate);
		emailgroup.setGroupclicksOpens(groupclicksOpens);
		emailgroup.setGroupdonationsClicks(groupdonationsClicks);
		emailgroup.setGroupdonorsClicks(groupdonorsClicks);
		emailgroup.setGroupunsubscribeRate(groupunsubscribeRate);
		emailgroup.setGroupbounceRate(groupbounceRate);
			/*for (int i = 0; i < emailgroup.getEmails().size(); i++) {
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
						if (email.getEmaildonationsum() != null) {
							//calculate donation info
							groupsum = groupsum + email.getEmaildonationsum(); 
							System.out.println("group sum: " + groupsum);
							groupdonationcount = groupdonationcount + email.getEmaildonationcount();
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
						if (email.getEmaildonationsum() != null) {
							//calculate donation info
							groupsum = groupsum + email.getEmaildonationsum(); 
							System.out.println(groupsum);
							groupdonationcount = groupdonationcount + email.getEmaildonationcount();
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
			groupdonorcount = donrepo.findDonorsinGroup(committee_id, emailGroupId);
			emailgroup.setGroupdonorcount(groupdonorcount);
			System.out.println(groupdonorcount);
			//recurring donor count
			groupRecurringDonorCount = donrepo.findRecurringDonorsinGroup(committee_id, emailGroupId);
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
		}*/
		egrepo.save(emailgroup);
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
