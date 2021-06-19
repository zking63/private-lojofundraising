package com.coding.LojoFundrasing.Models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
    @OneToMany(fetch=FetchType.LAZY, mappedBy="emailgroup")
	private List<Emails> Emails;
}
