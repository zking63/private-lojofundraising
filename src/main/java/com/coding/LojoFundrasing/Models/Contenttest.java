package com.coding.LojoFundrasing.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table (name="contenttest")
public class Contenttest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern ="dd/MM/yyyy")
	private Date senddate;
	private String type;
	private String RecipientsList;
	private Long recipients;
	private String name;
	private String jtk;
	private String topic;
	private String test;
	private String fullistWinner;
	private String GoWinner;
	private String ClickRcvWinner;
	private String VariantA;
	private Long ARecipientNumber;
	private Double AClickRate;
	private Long AOpens;
	private Double AGiftOpens;
	private String VariantB;
	private Long BRecipientNumber;
	private Double BClickRate;
	private Long BOpens;
	private Double BGiftOpens;
}
