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
		Integer count = 0;
		test test = content.getBigtest();
		AddContenttoTest(content);
		System.out.println("content size: " + test.getContent().size());
		test.setFullsendCountType(test.getContent().size());
		test.setClickWinnerCountType(test.getContent().size());
		test.setGoWinnerCountType(test.getContent().size());
		if (test.getVariantA() == null && test.getVariantB() == null) {
			test.setVariantA(content.getVariantA());
			test.setVariantA(content.getVariantB());
			if (content.getGoWinner() == test.getVariantA()) {
				count = test.getGoWinnerCountType1();
				count++;
				test.setGoWinnerCountType1(count);
			}
			if (content.getGoWinner() == test.getVariantB()) {
				count = test.getGoWinnerCountType2();
				count++;
				test.setGoWinnerCountType2(count);
			}
			if (content.getClickRcvWinner() == test.getVariantA()) {
				count = test.getClickWinnerCountType1();
				count++;
				test.setClickWinnerCountType1(count);
			}
			if (content.getClickRcvWinner() == test.getVariantB()) {
				count = test.getClickWinnerCountType2();
				count++;
				test.setClickWinnerCountType2(count);
			}
			if (content.getFullistWinner() == test.getVariantA()) {
				count = test.getFullsendCountType1();
				count++;
				test.setFullsendCountType1(count);
			}
			if (content.getFullistWinner() == test.getVariantB()) {
				count = test.getFullsendCountType2();
				count++;
				test.setFullsendCountType2(count);
			}
		}
		else {
			if (content.getGoWinner() == test.getVariantA()) {
				count = test.getGoWinnerCountType1();
				count++;
				test.setGoWinnerCountType1(count);
			}
			if (content.getGoWinner() == test.getVariantB()) {
				count = test.getGoWinnerCountType2();
				count++;
				test.setGoWinnerCountType2(count);
			}
			if (content.getClickRcvWinner() == test.getVariantA()) {
				count = test.getClickWinnerCountType1();
				count++;
				test.setClickWinnerCountType1(count);
			}
			if (content.getClickRcvWinner() == test.getVariantB()) {
				count = test.getClickWinnerCountType2();
				count++;
				test.setClickWinnerCountType2(count);
			}
			if (content.getFullistWinner() == test.getVariantA()) {
				count = test.getFullsendCountType1();
				count++;
				test.setFullsendCountType1(count);
			}
			if (content.getFullistWinner() == test.getVariantB()) {
				count = test.getFullsendCountType2();
				count++;
				test.setFullsendCountType2(count);
			}
		}
	}
}
