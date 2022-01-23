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
@Table (name="links")
public class Link {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="overalllink")
	private List<Emails> emails;
    
    private Integer donations;
    private Integer donors;
    
    private Long clicksFromEmail;
    
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Emails> getEmails() {
		return emails;
	}
	public void setEmails(List<Emails> emails) {
		this.emails = emails;
	}
	public Integer getDonations() {
		return donations;
	}
	public void setDonations(Integer donations) {
		this.donations = donations;
	}
	public Integer getDonors() {
		return donors;
	}
	public void setDonors(Integer donors) {
		this.donors = donors;
	}
	public Long getClicksFromEmail() {
		return clicksFromEmail;
	}
	public void setClicksFromEmail(Long clicksFromEmail) {
		this.clicksFromEmail = clicksFromEmail;
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
