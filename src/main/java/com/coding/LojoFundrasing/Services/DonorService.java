package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.DonorData;
import com.coding.LojoFundrasing.Repos.DonationRepo;
import com.coding.LojoFundrasing.Repos.DonorDataRepo;
import com.coding.LojoFundrasing.Repos.DonorRepo;


@Service
public class DonorService {
	@Autowired
	private DonorRepo drepo;
	
	@Autowired
	private DonorDataRepo dondrepo;
	
	@Autowired
	private DonationRepo donationrepo;
	
	public Donor createDonor(Donor donor) {
		System.out.println("new donor created");
		return drepo.save(donor);
	}
	public Donor updateDonor(Donor donor) {
		return drepo.save(donor);
	}
	public Donor findDonorbyEmail(String email) {
		return drepo.findBydonorEmail(email).orElse(null);
	}
	public Donor findDonorByEmailandCommittee(String email, Long committee_id) {
		return drepo.findByemailandCommittee(email, committee_id).orElse(null);
	}
	public Donor findDonorByIdandCommittee(Long id, Long committee_id) {
		return drepo.findByIDandCommittee(id, committee_id);
	}
	public List<Donor> allDonors() {
		return drepo.findAll();
	}
	public List<Donor> allDonorsinCommittee(Long committee_id) {
		return drepo.findDonorsinCommittee(committee_id);
	}
	public Donor findbyId(long id) {
		return drepo.findById(id).orElse(null);
	}
	public void delete(long id) {
		drepo.deleteById(id);
	}
	public Date getMostRecentdonation(Long id) {
	Donor donor = drepo.findById(id).orElse(null);
	List<Donation> contributions = donor.getContributions();
	List<Date> dates = new ArrayList<Date>();
	if (contributions.size() > 0) {
		for (int i = 0; i < contributions.size(); i++) {
			dates.add(contributions.get(i).getDondate());
			}
	}
	Date mostRecent = Collections.max(dates);
	return mostRecent;
    }
	public List<Donation> getDonationsbydonor(Long id){
		Donor donor = drepo.findById(id).orElse(null);
		List<Donation> contributions = donor.getContributions();
		return contributions;
	}
	
	public DonorData getDonorData(Donor donor, Long committee_id) {
		DonorData donordata = donor.getDonordata();
		Long id = donor.getId();
		Double daverage = 0.0;
		Double donorsum = 0.0;
		Integer donationcount = 0;
		Long mostrecent_donation_id = null;
		//Donation mostrecent = donor.getMostrecentDonationbyDonor();
		Date mostrecentDate = null;
		Date mostrecenttime = null;
		Double mostrecentamount = 0.0;
		List<DonorData> allDonordata = dondrepo.findAll();
		if (donordata != null) {
			if (dondrepo.findById(donordata.getId()) != null) {
			 //for (int i = 0; i < allDonordata.size(); i++) {
				 System.out.println(donordata.getDatadonor().getDonorFirstName());
					//if (id == allDonordata.get(i).getDatadonor().getId()) {
						System.out.println("donor id " + id);
						donordata = dondrepo.findById(donordata.getId()).orElse(null);
						Long ddid = donordata.getId();
						System.out.println("donordata id " + ddid);
						//ddid = allDonordata.get(i).getId();
						donordata = dondrepo.findById(ddid).orElse(null);
						daverage = drepo.donoraverages(id);
						donorsum = drepo.donorsums(id);
						donationcount = drepo.donordoncount(id);
						donordata.setDonoraverage(daverage);
						System.out.println("average " + daverage);
						donordata.setDonor_contributioncount(donationcount);
						System.out.println("contribution " + donationcount);
						donordata.setDonorsum(donorsum);
						System.out.println("sum " + donorsum);
						mostrecent_donation_id = drepo.mostRecentDonationDate(id);
						Donation mostrecent = donationrepo.findById(mostrecent_donation_id).orElse(null);
						mostrecentDate = mostrecent.getDondate();
						//mostrecenttime = mostrecent.getDontime();
						mostrecentamount = mostrecent.getAmount();
						donor.setMostrecentDate(mostrecentDate);
						donor.setMostrecentamount(mostrecentamount);
						//donor.setMostrecenttime(mostrecenttime);
						//donordata.setMostrecent_donation(mostrecent_donation_id);
						//donor.setMostrecentDonationbyDonor(mostrecent);
						System.out.println("date " + mostrecent_donation_id);
						return dondrepo.save(donordata);
					//}
				}
			    System.out.println("make");
				return dondrepo.save(donordata);
		}
		else {
			System.out.println("new");
			daverage = drepo.donoraverages(id);
			donorsum = drepo.donorsums(id);
			donationcount = drepo.donordoncount(id);
			mostrecent_donation_id = drepo.mostRecentDonationDate(id);
			Donation mostrecent = donationrepo.findById(mostrecent_donation_id).orElse(null);
			mostrecentDate = mostrecent.getDondate();
			//mostrecenttime = mostrecent.getDontime();
			mostrecentamount = mostrecent.getAmount();
			donor.setMostrecentDate(mostrecentDate);
			donor.setMostrecentamount(mostrecentamount);
			//donor.setMostrecenttime(mostrecenttime);
			//donor.setMostrecentDonationbyDonor(mostrecent);
			System.out.println("donor average:" + daverage);
			donordata = new DonorData(donor, daverage, donorsum, donationcount);
			System.out.println("donordata id: " + donordata.getId());
			return dondrepo.save(donordata);
		}
	}
	public List<Donor> orderMostRecentbyDonorDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findAllWithMostRecent(startdate, enddate, committee_id);
	}
	public List<Donor> orderMostRecentbyDonorAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findAllWithMostRecentDondateAfterAsc(startdate, enddate, committee_id);
	}
	public List<Donor> orderDonorCountDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByContributionCountByDesc(startdate, enddate, committee_id);
	}
	public List<Donor> orderDonorCountAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByContributionCountByAsc(startdate, enddate, committee_id);
	}
	public List<Donor> orderAverageAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByDonorAverageByAsc(startdate, enddate, committee_id);
	}
	public List<Donor> orderAverageDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByDonorAverageByDesc(startdate, enddate, committee_id);
	}
	public List<Donor> orderDonorsumDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByDonorsumByDesc(startdate, enddate, committee_id);
	}
	public List<Donor> orderDonorsumAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.findByDonorsumByAsc(startdate, enddate, committee_id);
	}
	public List<Donor> orderMostrecentAmountDesc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.MostrecentamountSortDesc(startdate, enddate, committee_id);
	}
	public List<Donor> orderMostrecentAmountAsc(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		return drepo.MostrecentamountSortAsc(startdate, enddate, committee_id);
	}
	public void DonorsWithinRange(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate, Long committee_id){
		List<Donor> donors = drepo.findDonorsWithinRangeandCommittee(startdate, enddate, committee_id);
		for (int i= 0; i < donors.size(); i++) {
			Long id = donors.get(i).getId();
			Integer countinrange = drepo.donordoncountRange(id, startdate, enddate, committee_id);
			Double suminrange = drepo.donorsumRange(id, startdate, enddate, committee_id);
			Double avginrange = drepo.donoravgRange(id, startdate, enddate, committee_id);
			Donor donor = drepo.findByIDandCommittee(id, committee_id);
			donor.setCountwithinrange(countinrange);
			donor.setSumwithinrange(suminrange);
			donor.setAveragewithinrange(avginrange);
			String name = donor.getDonorFirstName();
			Integer count = donor.getCountwithinrange();
			Double sum = donor.getSumwithinrange();
			Double average = donor.getAveragewithinrange();
			System.out.println("Donor " + name);
			System.out.println(" Count " + count);
			System.out.println(" Sum " + suminrange);
			System.out.println(" Sum from donor " + sum);
			System.out.println(" Average from donor " + average);
			System.out.println(" Average " + avginrange);
			System.out.println(" ID " + donor.getId());
		}
	}
	/*public Donor setCountinRange(@Param("startdate") @DateTimeFormat(pattern ="yyyy-MM-dd") String startdate, 
			@Param("enddate") @DateTimeFormat(pattern ="yyyy-MM-dd") String enddate){
		return drepo.MostrecentamountSortAsc(startdate, enddate);
	}*/
}
