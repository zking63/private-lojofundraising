package com.coding.LojoFundrasing.Models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="emailgroups")
public class EmailGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String emailgroupName;
	private Long groupOpeners;
	private Long groupRecipients;
	private Long groupClicks;
	private Long groupBounces;
	private Long groupUnsubscribers;
	private Integer groupRecurringDonorCount;
	private Integer groupRecurringDonationCount;
	private Double groupRecurringRevenue;
	private Double groupaverage;
	private Double groupsum;
	private Integer groupdonationcount;
	private Integer groupdonorcount;
	private Double groupunsubscribeRate;
	private Double groupclickRate;
	private Double groupopenRate;
	private Double groupbounceRate;
	private Double groupdonationsOpens;
	private Double groupdonationsClicks;
	private Double groupclicksOpens;
	private Double groupdonorsOpens;
	private Double groupdonorsClicks;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="emailgroup")
	private List<Emails> Emails;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User group_creator;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="committees_id")
    private Committees committee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmailgroupName() {
		return emailgroupName;
	}
	public void setEmailgroupName(String emailgroupName) {
		this.emailgroupName = emailgroupName;
	}
	public Long getGroupOpeners() {
		return groupOpeners;
	}
	public void setGroupOpeners(Long groupOpeners) {
		this.groupOpeners = groupOpeners;
	}
	public Long getGroupRecipients() {
		return groupRecipients;
	}
	public void setGroupRecipients(Long groupRecipients) {
		this.groupRecipients = groupRecipients;
	}
	public Long getGroupClicks() {
		return groupClicks;
	}
	public void setGroupClicks(Long groupClicks) {
		this.groupClicks = groupClicks;
	}
	public Long getGroupBounces() {
		return groupBounces;
	}
	public void setGroupBounces(Long groupBounces) {
		this.groupBounces = groupBounces;
	}
	public Long getGroupUnsubscribers() {
		return groupUnsubscribers;
	}
	public void setGroupUnsubscribers(Long groupUnsubscribers) {
		this.groupUnsubscribers = groupUnsubscribers;
	}
	public Integer getGroupRecurringDonorCount() {
		return groupRecurringDonorCount;
	}
	public void setGroupRecurringDonorCount(Integer groupRecurringDonorCount) {
		this.groupRecurringDonorCount = groupRecurringDonorCount;
	}
	public Integer getGroupRecurringDonationCount() {
		return groupRecurringDonationCount;
	}
	public void setGroupRecurringDonationCount(Integer groupRecurringDonationCount) {
		this.groupRecurringDonationCount = groupRecurringDonationCount;
	}
	public Double getGroupRecurringRevenue() {
		return groupRecurringRevenue;
	}
	public void setGroupRecurringRevenue(Double groupRecurringRevenue) {
		this.groupRecurringRevenue = groupRecurringRevenue;
	}
	public List<Emails> getEmails() {
		return Emails;
	}
	public void setEmails(List<Emails> emails) {
		Emails = emails;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getGroup_creator() {
		return group_creator;
	}
	public void setGroup_creator(User group_creator) {
		this.group_creator = group_creator;
	}
	public Committees getCommittee() {
		return committee;
	}
	public void setCommittee(Committees committee) {
		this.committee = committee;
	}
	public Double getGroupaverage() {
		return groupaverage;
	}
	public void setGroupaverage(Double groupaverage) {
		this.groupaverage = groupaverage;
	}
	public Double getGroupsum() {
		return groupsum;
	}
	public void setGroupsum(Double groupsum) {
		this.groupsum = groupsum;
	}
	public Integer getGroupdonationcount() {
		return groupdonationcount;
	}
	public void setGroupdonationcount(Integer groupdonationcount) {
		this.groupdonationcount = groupdonationcount;
	}
	public Integer getGroupdonorcount() {
		return groupdonorcount;
	}
	public void setGroupdonorcount(Integer groupdonorcount) {
		this.groupdonorcount = groupdonorcount;
	}
	public Double getGroupunsubscribeRate() {
		return groupunsubscribeRate;
	}
	public void setGroupunsubscribeRate(Double groupunsubscribeRate) {
		this.groupunsubscribeRate = groupunsubscribeRate;
	}
	public Double getGroupclickRate() {
		return groupclickRate;
	}
	public void setGroupclickRate(Double groupclickRate) {
		this.groupclickRate = groupclickRate;
	}
	public Double getGroupopenRate() {
		return groupopenRate;
	}
	public void setGroupopenRate(Double groupopenRate) {
		this.groupopenRate = groupopenRate;
	}
	public Double getGroupbounceRate() {
		return groupbounceRate;
	}
	public void setGroupbounceRate(Double groupbounceRate) {
		this.groupbounceRate = groupbounceRate;
	}
	public Double getGroupdonationsOpens() {
		return groupdonationsOpens;
	}
	public void setGroupdonationsOpens(Double groupdonationsOpens) {
		this.groupdonationsOpens = groupdonationsOpens;
	}
	public Double getGroupdonationsClicks() {
		return groupdonationsClicks;
	}
	public void setGroupdonationsClicks(Double groupdonationsClicks) {
		this.groupdonationsClicks = groupdonationsClicks;
	}
	public Double getGroupclicksOpens() {
		return groupclicksOpens;
	}
	public void setGroupclicksOpens(Double groupclicksOpens) {
		this.groupclicksOpens = groupclicksOpens;
	}
	public Double getGroupdonorsOpens() {
		return groupdonorsOpens;
	}
	public void setGroupdonorsOpens(Double groupdonorsOpens) {
		this.groupdonorsOpens = groupdonorsOpens;
	}
	public Double getGroupdonorsClicks() {
		return groupdonorsClicks;
	}
	public void setGroupdonorsClicks(Double groupdonorsClicks) {
		this.groupdonorsClicks = groupdonorsClicks;
	}
	
}
