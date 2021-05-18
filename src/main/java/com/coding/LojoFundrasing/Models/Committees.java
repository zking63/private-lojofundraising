package com.coding.LojoFundrasing.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name="committees")
public class Committees {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String CommitteeName;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "committees_users", 
	        joinColumns = @JoinColumn(name = "committees_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "committees_donations", 
	        joinColumns = @JoinColumn(name = "committees_id"), 
	        inverseJoinColumns = @JoinColumn(name = "donation_id")
			)
	private List<Donation> donations;
	
	public Committees() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommitteeName() {
		return CommitteeName;
	}
	public void setCommitteeName(String committeeName) {
		CommitteeName = committeeName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Donation> getDonations() {
		return donations;
	}
	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
	
}