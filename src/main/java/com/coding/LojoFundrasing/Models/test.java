package com.coding.LojoFundrasing.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="test")
public class test {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

}
