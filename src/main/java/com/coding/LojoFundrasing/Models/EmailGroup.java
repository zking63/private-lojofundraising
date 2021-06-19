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
}
