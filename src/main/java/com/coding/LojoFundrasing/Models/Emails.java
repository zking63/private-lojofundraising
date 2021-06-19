package com.coding.LojoFundrasing.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table (name="emails")
public class Emails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String emailName;
	private Long openers;
	private Long recipients;
	private String list;
	private String excludedList;
	private Long clicks;
	private Long bounces;
	private Long unsubscribers;
	private Integer recurringDonorCount;
	private Integer recurringDonationCount;
	private Double recurringRevenue;
	@DateTimeFormat(pattern ="yyyy-MM-dd kk:mm")
	private Date Emaildate;
	//private Double email_average;
	@NotNull
	private String emailRefcode;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User email_uploader;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="committees_id")
    private Committees committee;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emailgroup_id")
    private EmailGroup emailgroup;
	
    @OneToMany(fetch=FetchType.LAZY, mappedBy="emailDonation")
	private List<Donation> Emaildonations;
    
	@OneToOne(mappedBy="dataemail", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Data emaildata;
	
	public Emails() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public Date getEmaildate() {
		return Emaildate;
	}

	public void setEmaildate(Date emaildate) {
		Emaildate = emaildate;
	}

	public String getEmailRefcode() {
		return emailRefcode;
	}

	public void setEmailRefcode(String emailRefcode) {
		this.emailRefcode = emailRefcode;
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


    public List<Donation> getEmaildonations() {
		return Emaildonations;
	}

	public void setEmaildonations(List<Donation> Emaildonations) {
		this.Emaildonations = Emaildonations;
	}

	public String getEmailDateFormatted() {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
    	return df.format(this.Emaildate);
    }
    
	public User getEmail_uploader() {
		return email_uploader;
	}

	public void setEmail_uploader(User email_uploader) {
		this.email_uploader = email_uploader;
	}
	
	public Data getEmaildata() {
		return emaildata;
	}

	public void setEmaildata(Data emaildata) {
		this.emaildata = emaildata;
	}
	
	

	public Committees getCommittee() {
		return committee;
	}

	public void setCommittee(Committees committee) {
		this.committee = committee;
	}

	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate(){
	    this.updatedAt = new Date();
	}

	public Long getOpeners() {
		return openers;
	}

	public void setOpeners(Long openers) {
		this.openers = openers;
	}

	public Long getRecipients() {
		return recipients;
	}

	public void setRecipients(Long recipients) {
		this.recipients = recipients;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getExcludedList() {
		return excludedList;
	}

	public void setExcludedList(String excludedList) {
		this.excludedList = excludedList;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getBounces() {
		return bounces;
	}

	public void setBounces(Long bounces) {
		this.bounces = bounces;
	}

	public Long getUnsubscribers() {
		return unsubscribers;
	}

	public void setUnsubscribers(Long unsubscribers) {
		this.unsubscribers = unsubscribers;
	}

	public Integer getRecurringDonorCount() {
		return recurringDonorCount;
	}

	public void setRecurringDonorCount(Integer recurringDonorCount) {
		this.recurringDonorCount = recurringDonorCount;
	}

	public Integer getRecurringDonationCount() {
		return recurringDonationCount;
	}

	public void setRecurringDonationCount(Integer recurringDonationCount) {
		this.recurringDonationCount = recurringDonationCount;
	}

	public Double getRecurringRevenue() {
		return recurringRevenue;
	}

	public void setRecurringRevenue(Double recurringRevenue) {
		this.recurringRevenue = recurringRevenue;
	}

	public EmailGroup getEmailgroup() {
		return emailgroup;
	}

	public void setEmailgroup(EmailGroup emailgroup) {
		this.emailgroup = emailgroup;
	}
	
	
}
