package com.coding.LojoFundrasing.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="contenttest")
public class Contenttest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}
