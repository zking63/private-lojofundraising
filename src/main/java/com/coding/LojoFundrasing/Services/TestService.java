package com.coding.LojoFundrasing.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.test;
import com.coding.LojoFundrasing.Repos.testrepo;

@Service
public class TestService {
	@Autowired
	private testrepo trepo;
	
	public test createTest(test test) {
		return trepo.save(test);
	}
	
	public test findTestByNameandCommittee(String testcategory, Long committee_id) {
		return trepo.findbyTest(testcategory, committee_id).orElse(null);
	}
}
