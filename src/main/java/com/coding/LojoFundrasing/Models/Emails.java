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
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date Emaildate;
	@DateTimeFormat(pattern ="kk:mm")
	private Date Emailtime;
	//private Double email_average;
	@NotNull
	private String emailRefcode;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User email_uploader;
	
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
	
	

	public Date getEmailtime() {
		return Emailtime;
	}

	public void setEmailtime(Date emailtime) {
		Emailtime = emailtime;
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
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	return df.format(this.Emaildate);
    }
    public String getEmailTimeFormatted() {
    	SimpleDateFormat df = new SimpleDateFormat("kk:mm");
    	return df.format(this.Emailtime);
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

	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate(){
	    this.updatedAt = new Date();
	}
	
}
