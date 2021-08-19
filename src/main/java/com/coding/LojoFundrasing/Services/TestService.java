package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Contenttest;
import com.coding.LojoFundrasing.Models.test;
import com.coding.LojoFundrasing.Repos.ContentTestRepo;
import com.coding.LojoFundrasing.Repos.testrepo;

@Service
public class TestService {
	@Autowired
	private testrepo trepo;
	
	@Autowired
	private ContentTestRepo ctrepo;
	
	public test createTest(test test) {
		return trepo.save(test);
	}
	
	public test findTestByNameandCommittee(String testcategory, Long committee_id) {
		return trepo.findbyTest(testcategory, committee_id).orElse(null);
	}
	
	public test AddContenttoTest(Contenttest content) {
		test test = content.getBigtest();
		if (test.getContent().size() == 0) {
			List<Contenttest> contentlist = new ArrayList<Contenttest>();
			contentlist.add(content);
			System.out.println(content.getId());
			System.out.println("Content list:" + contentlist);
			test.setContent(contentlist);
			return trepo.save(test);
		}
		else {
			for (int i = 0; i < test.getContent().size() +1; i++) {
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
	
	/*private String VariantA;
    private String VariantB;
    private Integer goWinnerCountType1;
    private Integer clickWinnerCountType1;
    private Integer fullsendCountType1;
    private Integer goWinnerCountType2;
    private Integer clickWinnerCountType2;
    private Integer fullsendCountType2;
    private Integer goWinnerCountType;
    private Integer clickWinnerCountType;
    private Integer fullsendCountType;*/
	
	public void TestVariables(Contenttest content) {
		test test = content.getBigtest();
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
		test.setGoWinnerCountType1(ctrepo.VariantAGoWinnerCount(test.getId(), test.getCommittee().getId(), test.getVariantA()));
		test.setGoWinnerCountType2(ctrepo.VariantBGoWinnerCount(test.getId(), test.getCommittee().getId(), test.getVariantB()));
		test.setClickWinnerCountType1(ctrepo.VariantAClickRcvWinner(test.getId(), test.getCommittee().getId(), test.getVariantA()));
		test.setClickWinnerCountType2(ctrepo.VariantBClickRcvWinner(test.getId(), test.getCommittee().getId(), test.getVariantB()));
		test.setFullsendCountType1(ctrepo.VariantAFulllistWinner(test.getId(), test.getCommittee().getId(), test.getVariantA()));
		test.setFullsendCountType2(ctrepo.VariantBFulllistWinner(test.getId(), test.getCommittee().getId(), test.getVariantB()));
		trepo.save(test);
	}
}
