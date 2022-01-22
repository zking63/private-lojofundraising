package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Contenttest;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.test;
import com.coding.LojoFundrasing.Repos.ContentTestRepo;
import com.coding.LojoFundrasing.Repos.testrepo;

@Service
public class TestService {
	@Autowired
	private testrepo trepo;
	
	@Autowired
	private ContentTestRepo ctrepo;
	
	@Autowired
	private CommitteeService cservice;
	
	public test createTest(test test) {
		return trepo.save(test);
	}
	public test updateTest(test test) {
		return trepo.save(test);
	}
	
	public test findTestByNameandCommittee(String testcategory, Long committee_id) {
		return trepo.findbyTest(testcategory, committee_id).orElse(null);
	}
	public test testSetUpTestfromGroup(Long committee_id, EmailGroup emailgroup) {
		String testcategory = emailgroup.getGroupTest();
		Committees committee = cservice.findbyId(committee_id);
		Boolean committeeSetList = false;
		Boolean emailgroupListSet = false;

		test test = trepo.findbyTest(testcategory, committee.getId()).orElse(null);
		if (test == null) {
			test = new test();
			System.out.println("NEW TEST");
			test.setTestcategory(testcategory);
			test.setVariantA(emailgroup.getVariantA());
			test.setVariantB(emailgroup.getVariantB());
        	while (committeeSetList == false) {
    			if (committee.getBigtest() == null || committee.getBigtest().size() == 0) {
    				test.setCommittee(committee);
    				List<test> tests = new ArrayList<test>();
    				tests.add(test);
    				committee.setBigtest(tests);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
    			else {
    				test.setCommittee(committee);
    				List<test> tests = committee.getBigtest();
    				tests.add(test);
    				committee.setBigtest(tests);
    				cservice.createCommittee(committee);
    				committeeSetList = true;
    			}
        	}
        	while (emailgroupListSet == false) {
    			if (test.getEmailgroups() == null || test.getEmailgroups().size() == 0) {
    				emailgroup.setTest(test);
    				List<EmailGroup> emailgroups = new ArrayList<EmailGroup>();
    				emailgroups.add(emailgroup);
    				test.setEmailgroups(emailgroups);
    				updateTest(test);
    				emailgroupListSet = true;
    			}
    			else {
    				emailgroup.setTest(test);
    				List<EmailGroup> emailgroups = test.getEmailgroups();
    				emailgroups.add(emailgroup);
    				test.setEmailgroups(emailgroups);
    				updateTest(test);
    				emailgroupListSet = true;
    			}
        	}
        	createTest(test);
        	return test;
		}
		else {
			System.out.println("OLD TEST");
			return test;
		}
	}
	
	public void CalculateTestData(test test, Committees committee) {
		if (test == null) {
			return;
		}
		
		String testcategory = null;
		Boolean committeeSetList = false;
		
	    Long variantARecipients = (long) 0;
	    Long variantBRecipients = (long) 0;
	    
	    Long variantAOpens;
	    Long variantBOpens;
	    
	    Long variantAClicks;
	    Long variantBClicks;
	    
	    Long variantADonations;
	    Long variantBDonations;
	    
	    Double variantARevenue;
	    Double variantBRevenue;
	    
	    Double variantAOpenRate;
	    Double variantBOpenRate;
	    
	    Double variantAClickOpens;
	    Double variantBClickOpens;
	    
	    Double variantAClickRate;
	    Double variantBClickRate;
	    
	    Double variantADonationsOpens;
	    Double variantBDonationsOpens;
	    
	    Double variantADonationsClicks;
	    Double variantBDonationsClicks;
	    
	    Double variantADonorsOpens;
	    Double variantBDonorsOpens;
	    
	    Double variantADonorsClicks;
	    Double variantBDonorsClicks;
	    
	    Double variantAaverageDonation;
	    Double variantBaverageDonation;

		variantARecipients = trepo.variantARecipients(committee.getId(), test.getId());
		variantBRecipients = trepo.variantBRecipients(committee.getId(), test.getId());
		System.out.println("variantARecipients " + variantARecipients);
		System.out.println("variantBRecipients " + variantBRecipients);
	}
	
	/*public test AddContenttoTest(Contenttest content) {
		test test = content.getBigtest();
		if (test.getContent() == null) {
			List<Contenttest> contentlist = new ArrayList<Contenttest>();
			contentlist.add(content);
			System.out.println(content.getId());
			System.out.println("Content list:" + contentlist);
			test.setContent(contentlist);
			return trepo.save(test);
		}
		else {
			for (int i = 0; i < test.getContent().size(); i++) {
				if (test.getContent().get(i).getId() == content.getId()) {
					System.out.println("Content i:" + test.getContent().get(i).getId());
					System.out.println("Content test:" + content.getId());
					return trepo.save(test);
				}
				else {
					System.out.println("Content i:" + test.getContent().get(i).getId());
					System.out.println("Content test:" + content.getId());
					i++;
				}
			}
			List<Contenttest> contentlist = test.getContent();
			contentlist.add(content);
			System.out.println(content.getId());
			System.out.println("Content list after loop:" + contentlist);
			test.setContent(contentlist);
			return trepo.save(test);
		}
	}
	
	public void TestVariables(Contenttest content) {
		test test = content.getBigtest();
		String varianta = "Variant A";
		String variantb = "Variant B";
		String tied = "TIED";
	    Double goWinnerPercentType1 = 0.0;
	    Double clickWinnerPercentType1 = 0.0;
	    Double fullsendPercentType1 = 0.0;
	    Double goWinnerPercentType2 = 0.0;
	    Double clickWinnerPercentType2 = 0.0;
	    Double fullsendPercentType2 = 0.0;
	    Double goWinnerPercentTied = 0.0;
	    Double clickWinnerPercentTied = 0.0;
	    Double fullsendPercentTied = 0.0;
	    
		AddContenttoTest(content);
		System.out.println("content size: " + test.getContent().size());
		test.setFullsendCountType(test.getContent().size());
		System.out.println("content size: " + test.getFullsendCountType());
		test.setClickWinnerCountType(test.getContent().size());
		test.setGoWinnerCountType(test.getContent().size());
		if (test.getVariantA() == null && test.getVariantB() == null) {
			test.setVariantA(content.getVariantA());
			test.setVariantB(content.getVariantB());
			System.out.println("variant A: " + test.getVariantA());
			System.out.println("variant B: " + test.getVariantB());
		}
		
		test.setGoWinnerCountType1(ctrepo.VariantAGoWinnerCount(test.getId(), test.getCommittee().getId(), varianta));
		test.setGoWinnerCountType2(ctrepo.VariantBGoWinnerCount(test.getId(), test.getCommittee().getId(), variantb));
		test.setGoWinnerCountTied(ctrepo.TiedGoWinnerCount(test.getId(), test.getCommittee().getId(), tied));
		test.setClickWinnerCountType1(ctrepo.VariantAClickRcvWinner(test.getId(), test.getCommittee().getId(), varianta));
		test.setClickWinnerCountType2(ctrepo.VariantBClickRcvWinner(test.getId(), test.getCommittee().getId(), variantb));
		test.setClickWinnerCountTied(ctrepo.TiedClickRcvWinner(test.getId(), test.getCommittee().getId(), tied));
		test.setFullsendCountType1(ctrepo.VariantAFulllistWinner(test.getId(), test.getCommittee().getId(), varianta));
		test.setFullsendCountType2(ctrepo.VariantBFulllistWinner(test.getId(), test.getCommittee().getId(), variantb));
		test.setFullsendCountTied(ctrepo.TiedFulllistWinner(test.getId(), test.getCommittee().getId(), tied));
		trepo.save(test);
		
		//casting to doubles
		Double gowinnertype1 = (double) test.getGoWinnerCountType1();
		Double gowinnertype2 = (double) test.getGoWinnerCountType2();
		Double gowinnertied = (double) test.getGoWinnerCountTied();
		Double gowinnercount = (double) test.getGoWinnerCountType();
		
		Double clickwinnertype1 = (double) test.getClickWinnerCountType1();
		Double clickwinnertype2 = (double) test.getClickWinnerCountType2();
		Double clickwinnertied = (double) test.getClickWinnerCountTied();
		Double clickwinnercount = (double) test.getClickWinnerCountType();
		
		Double fullsendwinnertype1 = (double) test.getFullsendCountType1();
		Double fullsendwinnertype2 = (double) test.getFullsendCountType2();
		Double fullsendwinnertied = (double) test.getFullsendCountTied();
		Double fullsendwinnercount = (double) test.getFullsendCountType();

		//GO Percents
		goWinnerPercentType1 = gowinnertype1/gowinnercount;
		test.setGoWinnerPercentType1(goWinnerPercentType1);
		goWinnerPercentType2 = gowinnertype2/gowinnercount;
		test.setGoWinnerPercentType2(goWinnerPercentType2);
		goWinnerPercentTied = gowinnertied/gowinnercount;
		test.setGoWinnerPercentTied(goWinnerPercentTied);
		
		//Click Percents
		clickWinnerPercentType1 = clickwinnertype1/clickwinnercount;
		test.setClickWinnerPercentType1(clickWinnerPercentType1);
		clickWinnerPercentType2 = clickwinnertype2/clickwinnercount;
		test.setClickWinnerPercentType2(clickWinnerPercentType2);
		clickWinnerPercentTied = clickwinnertied/clickwinnercount;
		test.setClickWinnerPercentTied(clickWinnerPercentTied);
		
		//Full Send Percents
		fullsendPercentType1 = fullsendwinnertype1/fullsendwinnercount;
		test.setFullsendPercentType1(fullsendPercentType1);
		fullsendPercentType2 = fullsendwinnertype2/fullsendwinnercount;
		test.setFullsendPercentType2(fullsendPercentType2);
		fullsendPercentTied = fullsendwinnertied/fullsendwinnercount;
		test.setFullsendPercentTied(fullsendPercentTied);
		
		trepo.save(test);
		
		if (test.getGoWinnerCountType1() > test.getGoWinnerCountType2()) {
			test.setOverallGoWinner(test.getVariantA());
			test.setOverallGoWinnerPercent(goWinnerPercentType1);
		}
		else if (test.getGoWinnerCountType1() == test.getGoWinnerCountType2()){
			test.setOverallGoWinner("TIED");
			test.setOverallGoWinnerPercent(goWinnerPercentType1);
		}
		else {
			test.setOverallGoWinner(test.getVariantB());
			test.setOverallGoWinnerPercent(goWinnerPercentType2);
		}
		if (test.getClickWinnerCountType1() > test.getClickWinnerCountType2()) {
			test.setOverallClickWinner(test.getVariantA());
			test.setOverallClickWinnerPercent(clickWinnerPercentType1);
		}
		else if (test.getClickWinnerCountType1() == test.getClickWinnerCountType2()){
			test.setOverallClickWinner("TIED");
			test.setOverallClickWinnerPercent(clickWinnerPercentType1);
		}
		else {
			test.setOverallClickWinner(test.getVariantB());
			test.setOverallClickWinnerPercent(clickWinnerPercentType2);
		}
		if (test.getFullsendCountType1() > test.getFullsendCountType2()) {
			test.setOverallFullSendWinner(test.getVariantA());
			test.setOverallFullSendWinnerPercent(fullsendPercentType1);
		}
		else if (test.getFullsendCountType1() == test.getFullsendCountType2()) {
			test.setOverallFullSendWinner("TIED");
			test.setOverallFullSendWinnerPercent(fullsendPercentType1);
		}
		else {
			test.setOverallFullSendWinner(test.getVariantB());
			test.setOverallFullSendWinnerPercent(fullsendPercentType2);
		}
		
		trepo.save(test);
		
	}
	
	public List<test> findAllTests(Long committee_id){
		return trepo.findTestsbyCommittee(committee_id);
	}*/
}
