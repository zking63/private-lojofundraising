package com.coding.LojoFundrasing.Models;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="data_funds")
public class Data {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double emailaverage;
	private Double emailsum;
	private Integer donationcount;
	private Integer donorcount;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="email_id")
    private Emails dataemail;
    
	public Data() {

	}
	
	public Data(Emails email, Double eaverage, Double emailsum, Integer donationcount, Integer donorcount) {
		this.dataemail = email;
		this.emailaverage = eaverage;
		this.emailsum = emailsum;
		this.donationcount = donationcount;
		this.donorcount = donorcount;
	}
	
	
	
	public Double getEmailAverage() {
		return emailaverage;
	}

	public void setEmailAverage(Double emailaverage) {
		this.emailaverage = emailaverage;
	}
	
	public String getEmailAverageFormatted() {
		if (this.emailaverage == null) {
			this.emailaverage = 0.0;
		}
		double emailAverage1 = (double) getEmailAverage();
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(emailAverage1);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Emails getDataEmail() {
		return dataemail;
	}
	public void setDataEmail(Emails dataemail) {
		this.dataemail = dataemail;
	}

	public Double getEmailsum() {
		return emailsum;
	}

	public void setEmailsum(Double emailsum) {
		this.emailsum = emailsum;
	}

	public Integer getDonationcount() {
		return donationcount;
	}

	public void setDonationcount(Integer donationcount) {
		this.donationcount = donationcount;
	}

	public Integer getDonorcount() {
		return donorcount;
	}

	public void setDonorcount(Integer donorcount) {
		this.donorcount = donorcount;
	}
}
