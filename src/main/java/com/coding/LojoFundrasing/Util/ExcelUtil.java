package com.coding.LojoFundrasing.Util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.User;
import com.coding.LojoFundrasing.Services.DonationService;
import com.coding.LojoFundrasing.Services.DonorService;
import com.coding.LojoFundrasing.Services.EmailService;
import com.coding.LojoFundrasing.Services.UserService;

@Component
public class ExcelUtil {
	@Autowired
	private DonorService dservice;
	@Autowired
	private EmailService eservice;
	@Autowired
	private DonationService donservice;
	@Autowired
	private UserService uservice;
	
	public void getSheetDetails(String excelPath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));

		// Retrieving the number of sheets in the Workbook
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		System.out.println("Retrieving Sheets using for-each loop");
		
		for (Sheet sheet : workbook) {
			System.out.println("=> " + sheet.getSheetName());
		}
	}
	
	public void readExcelSheet(String excelPath, Long user_id, Committees committee)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		List<String> list = new ArrayList<String>();

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));
		System.out.println("workbook created");

		int x = workbook.getNumberOfSheets();
		
		System.out.println("number of sheets " + x);

		int noOfColumns = 0;
		List<Cell> headers = new ArrayList<Cell>();
		Cell header = null;
		Cell value = null;
		List<Cell> values = new ArrayList<Cell>();
		
		// Getting the Sheet at index zero
		for (int i = 0; i < x; i++) {

			Sheet sheet1 = workbook.getSheetAt(i);
			
			System.out.println("sheet1 " + sheet1);
			Iterator<Row> rowIterator = sheet1.iterator();

			noOfColumns = sheet1.getRow(i).getLastCellNum();
			
			System.out.println("number of columns " + noOfColumns);
			

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();
			int NameColumn = 0;
			int EmailColumn = 0;
			int LastNameColumn = 0;
			int AmountColumn = 0;
			int TimeColumn = 0;
			int RefcodeColumn = 0;
			int DateColumn = 0;
			int AbIdColumn = 0;
			int RecurringColumn = 0;
			int RecurrenceColumn = 0;
			int addressColumn = 0;
			int cityColumn = 0;
			int zipColumn = 0;
			int stateColumn = 0; 
			int countryColumn = 0;
			int phoneColumn = 0;
			User uploader = uservice.findUserbyId(user_id);
			String address = null;
			String city = null;
			String state = null;
			String Zipcode = null;
			String country = null;
			String phone = null;
			String emailValue = null;
			String nameValue = null;
			String LNValue = null;
			Double amount = null;
			String refcode = null;
			String ActBlueId = null;
			String Recurring = null;
			Integer Recurrence = null;
			Date date = null;
			Donor donor = null;
			Date dateValue = new Date();
			Date timeValue = null;
			Donation donation  = null;
        	List<Committees> committees = null;
        	List<Donation> donations = null;
        	List<Donor> donors = null;
			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {

	                   
	                	Cell cell = cellIterator.next();
	                	System.out.println("CELL: " + cell.getAddress());
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							System.out.println("Header: " + header);
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							
							String headerValue = dataFormatter.formatCellValue(header).toUpperCase();
							if (headerValue.contains("FIRST NAME")) {
								NameColumn = header.getColumnIndex();
							}
							if (headerValue.contains("LAST NAME")) {
								LastNameColumn = header.getColumnIndex();
							}
							if (headerValue.contains("EMAIL")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("refcode: " + headerValue);
							}
							if (headerValue.contains("REFERENCE CODE")) {
								RefcodeColumn = header.getColumnIndex();
								System.out.println("refcode: " + headerValue);
							}
							if (headerValue.contains("AMOUNT")) {
								AmountColumn = header.getColumnIndex();
							}
							if (headerValue.contains("DATE")) {
								DateColumn = header.getColumnIndex();
							}
							if (headerValue.contains("RECEIPT")) {
								AbIdColumn = header.getColumnIndex();
							}
							if (headerValue.contains("RECURRING")) {
								RecurringColumn = header.getColumnIndex();
							}
							if (headerValue.contains("RECURRENCE")) {
								RecurrenceColumn = header.getColumnIndex();
							}
							if (headerValue.contains("ADDR")) {
								addressColumn = header.getColumnIndex();
							}
							if (headerValue.contains("CITY")) {
								cityColumn = header.getColumnIndex();
							}
							if (headerValue.contains("STATE")) {
								stateColumn = header.getColumnIndex();
							}
							if (headerValue.contains("ZIP")) {
								zipColumn = header.getColumnIndex();
							}
							if (headerValue.contains("COUNTRY")) {
								countryColumn = header.getColumnIndex();
							}
							if (headerValue.contains("PHONE")) {
								phoneColumn = header.getColumnIndex();
							}
							System.out.println("Headers: " + headers);
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									/*System.out.println("Value: " + value);
									values.add(value);
									System.out.println("Values: " + values);
									System.out.println("-----header check-----");*/
									if (cell.getColumnIndex() == EmailColumn) {
										emailValue = dataFormatter.formatCellValue(cell);
										System.out.println("Email:" + emailValue);
									}
									else if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										System.out.println(nameValue);
										/*System.out.print("map: " + userMap);
								        System.out.print("usermap section");*/
								        /*Set<String> keys = userMap.keySet();
								        System.out.print(keys);
								        for(String key : keys) {
								            System.out.println(key);
								            System.out.println(userMap.get(key));    
								        }*/
									}
									else if (cell.getColumnIndex() == LastNameColumn) {
										LNValue = dataFormatter.formatCellValue(cell);
										System.out.println(LNValue);
									}
									else if (cell.getColumnIndex() == AbIdColumn) {
										ActBlueId = dataFormatter.formatCellValue(cell);
										System.out.println("ActBlue Id: " + ActBlueId);
									}
									else if (cell.getColumnIndex() == AmountColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										amount = Double.parseDouble(amount1); 
										System.out.println(amount);
									}
									else if (cell.getColumnIndex() == DateColumn) {
										String dateValue1 = dataFormatter.formatCellValue(cell);
										date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										System.out.println(dateValue1);
										System.out.println("Simple date: " + date);
									}
									else if (cell.getColumnIndex() == RecurringColumn) {
										Recurring = dataFormatter.formatCellValue(cell);
										System.out.println("Recurring: " + Recurring);
									}
									else if (cell.getColumnIndex() == RecurrenceColumn) {
										Recurrence = Integer.parseInt(dataFormatter.formatCellValue(cell));
										System.out.println("Recurrence: " + Recurrence);
									}
									else if (cell.getColumnIndex() == addressColumn) {
										address = dataFormatter.formatCellValue(cell);
										System.out.println("Address: " + address);
									}
									else if (cell.getColumnIndex() == cityColumn) {
										city = dataFormatter.formatCellValue(cell);
										System.out.println("City: " + city);
									}
									else if (cell.getColumnIndex() == stateColumn) {
										state = dataFormatter.formatCellValue(cell);
										System.out.println("State: " + state);
									}
									else if (cell.getColumnIndex() == zipColumn) {
										Zipcode = dataFormatter.formatCellValue(cell);
										System.out.println("Zip: " + Zipcode);
									}
									else if (cell.getColumnIndex() == countryColumn) {
										country = dataFormatter.formatCellValue(cell);
										System.out.println("Country: " + country);
									}
									else if (cell.getColumnIndex() == phoneColumn) {
										phone = dataFormatter.formatCellValue(cell);
										System.out.println("Phone: " + phone);
									}
									else if (cell.getColumnIndex() == RefcodeColumn) {
										refcode = dataFormatter.formatCellValue(cell);
										System.out.println("Refcode: " + refcode);
										System.out.println("EMAIL AFTER: " + emailValue);
										System.out.println("REFCODE AFTER: " + refcode);
										System.out.println("Name AFTER: " + nameValue);
										System.out.println("LN AFTER: " + LNValue);
										System.out.println("AMOUNT AFTER: " + amount);
										System.out.println("DATE AFTER: " + date);
										System.out.println("UPLOADER: " + uploader.getId());
										System.out.println("AB ID: " + ActBlueId);
										System.out.println("Recurring: " + Recurring);
										System.out.println("Recurrence: " + Recurrence);
										System.out.println("ADDRESS AFTER: " + address);
										System.out.println("CITY AFTER: " + city);
										System.out.println("STATE AFTER: " + state);
										System.out.println("COUNTRY AFTER: " + country);
										System.out.println("PHONE AFTER: " + phone);
										System.out.println("ZIP AFTER: " + Zipcode);
							    	   if (dservice.findDonorByEmailandCommittee(emailValue, committee.getId()) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	donor.setUploader(uploader);
					    	        	donor.setCommittee(committee);
					    	        	donor.setAddress(address);
					    	        	donor.setCity(city);
					    	        	donor.setCountry(country);
					    	        	donor.setPhone(phone);
					    	        	donor.setZipcode(Zipcode);
					    	        	donor.setState(state);
					    	        	donors = committee.getDonors();
					    	        	donors.add(donor);
					    	        	committee.setDonors(donors);
					    	        	System.out.println("UPLOADER FROM DONOR: " + donor.getUploader().getId());
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setActBlueId(ActBlueId);
					    	        	donation.setRecurrenceNumber(Recurrence);
					    	        	donation.setRecurring(Recurring);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findDonorByIdandCommittee(id, committee.getId()));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcodeandCommittee(refcode, committee));
					    	        	donation.setDonation_uploader(uploader);
					    	        	System.out.println("committee after: " + committee.getCommitteeName());
					    	        	//committees.add(committee);
					    	        	donation.setCommittee(committee);
					    	        	committee.setDonations(donations);
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	donservice.createDonation(donation);
					    	    		Emails email = donation.getEmailDonation();
					    	    		eservice.getEmailData(email, committee.getId());
					    	    		dservice.getDonorData(donor, committee.getId());
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorByEmailandCommittee(emailValue, committee.getId());
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	System.out.println("committee after: " + committee.getCommitteeName());
					    	        	donor.setUploader(uploader);
					    	        	donor.setCommittee(committee);
					    	        	donor.setAddress(address);
					    	        	donor.setCity(city);
					    	        	donor.setCountry(country);
					    	        	donor.setPhone(phone);
					    	        	donor.setZipcode(Zipcode);
					    	        	donor.setState(state);
					    	        	donors = committee.getDonors();
					    	        	donors.add(donor);
					    	        	committee.setDonors(donors);
					    	        	System.out.println("UPLOADER FROM DONOR: " + donor.getUploader().getId());
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setActBlueId(ActBlueId);
					    	        	donation.setRecurrenceNumber(Recurrence);
					    	        	donation.setRecurring(Recurring);
					    	        	donation.setAmount(amount);
					    	        	System.out.println("amount");
					    	        	donation.setDondate(date);
					    	        	System.out.println("date");
					    	        	donation.setDonor(dservice.findDonorByIdandCommittee(id, committee.getId()));
					    	        	System.out.println("donor");
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcodeandCommittee(refcode, committee));
					    	        	System.out.println("refcode");
					    	        	donation.setDonation_uploader(uploader);
					    	        	System.out.println("uploader");
					    	        	System.out.println("get committees " + committees);
					    	        	donations = committee.getDonations();
					    	        	System.out.println("get donations " + donations);
					    	        	donations.add(donation);
					    	        	System.out.println("add donation");
					    	        	committee.setDonations(donations);
					    	        	System.out.println("set donations " + donations);
					    	        	donation.setCommittee(committee);
					    	        	/*committees.add(committee);
					    	        	System.out.println("add committee");
					    	        	donation.setCommittees(committees);
					    	        	System.out.println("set committees" + committees);*/
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("create donation");
					    	    		Emails email = donation.getEmailDonation();
					    	    		eservice.getEmailData(email, committee.getId());
					    	    		dservice.getDonorData(donor, committee.getId());
					    	        	refcode = null;
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }
									}
							//}
							/*else {
							System.out.println("-----check-----");
									System.out.println("EMAIL AFTER: " + emailValue);
									System.out.println("REFCODE AFTER: " + refcode);
									System.out.println("Name AFTER: " + nameValue);
									System.out.println("LN AFTER: " + LNValue);
									System.out.println("AMOUNT AFTER: " + amount);
									System.out.println("DATE AFTER: " + date);
									donor = dservice.findDonorbyEmail(emailValue);
									System.out.println("DONOR: " + dservice.findDonorbyEmail(emailValue));*/
					    	       /*if (dservice.findDonorbyEmail(emailValue) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorbyEmail(emailValue);
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }*/
								//}
							}
		    	        }

	            }
		}
	}
	public void readExcelSheetEmails(String excelPath, Long user_id, Committees committee)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		List<String> list = new ArrayList<String>();

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));
		System.out.println("workbook created");

		int x = workbook.getNumberOfSheets();
		
		System.out.println("number of sheets " + x);

		int noOfColumns = 0;
		List<Cell> headers = new ArrayList<Cell>();
		Cell header = null;
		Cell value = null;
		List<Cell> values = new ArrayList<Cell>();
		
		// Getting the Sheet at index zero
		for (int i = 0; i < x; i++) {

			Sheet sheet1 = workbook.getSheetAt(i);
			
			System.out.println("sheet1 " + sheet1);
			Iterator<Row> rowIterator = sheet1.iterator();

			noOfColumns = sheet1.getRow(i).getLastCellNum();
			
			System.out.println("number of columns " + noOfColumns);
			

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();
			int NameColumn = 0;
			int RefcodeColumn = 0;
			int DateColumn = 0;
			User uploader = uservice.findUserbyId(user_id);
			String nameValue = null;
			String refcode = null;
			Emails email = null;
			Date date = null;
			Date dateValue = new Date();
			List<Emails> emails = null;
			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {

	                   
	                	Cell cell = cellIterator.next();
	                	System.out.println("CELL: " + cell.getAddress());
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							System.out.println("Header: " + header);
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							
							String headerValue = dataFormatter.formatCellValue(header).toUpperCase();
							if (headerValue.contains("NAME")) {
								NameColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("DATE")) {
								DateColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("REFCODE")) {
								RefcodeColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							System.out.println("Headers: " + headers);
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									/*System.out.println("Value: " + value);
									values.add(value);
									System.out.println("Values: " + values);
									System.out.println("-----header check-----");*/
									if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										System.out.println(nameValue);
										/*System.out.print("map: " + userMap);
								        System.out.print("usermap section");*/
								        /*Set<String> keys = userMap.keySet();
								        System.out.print(keys);
								        for(String key : keys) {
								            System.out.println(key);
								            System.out.println(userMap.get(key));    
								        }*/
									}
									else if (cell.getColumnIndex() == DateColumn) {
										String dateValue1 = dataFormatter.formatCellValue(cell);
										date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										System.out.println(dateValue1);
										System.out.println("Simple date: " + date);
									}
									else if (cell.getColumnIndex() == RefcodeColumn) {
										refcode = dataFormatter.formatCellValue(cell);
										System.out.println("REFCODE AFTER: " + refcode);
										System.out.println("Name AFTER: " + nameValue);
										System.out.println("DATE AFTER: " + date);
							    	if (eservice.findEmailbyRefcodeandCommittee(refcode, committee) == null) {
					    	        	email = new Emails();
					    	        	//System.out.println("ID: " + id);
					    	        	email.setEmailName(nameValue);
					    	        	email.setEmaildate(date);
					    	        	email.setEmailRefcode(refcode);
					    	        	email.setEmail_uploader(uploader);
					    	        	email.setCommittee(committee);
					    	        	emails = committee.getEmails();
					    	        	emails.add(email);
					    	        	committee.setEmails(emails);
					    	        	eservice.createEmail(email);
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + email.getId() + " Email: " + email.getEmailRefcode());
					    	        }
					    	        else {
					    	        	email = eservice.findEmailbyRefcodeandCommittee(refcode, committee);
					    	        	email.setEmailName(nameValue);
					    	        	email.setEmaildate(date);
					    	        	email.setEmailRefcode(refcode);
					    	        	email.setEmail_uploader(uploader);
					    	        	email.setCommittee(committee);
					    	        	emails = committee.getEmails();
					    	        	emails.add(email);
					    	        	committee.setEmails(emails);
					    	        	eservice.createEmail(email);
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + email.getId() + " Email: " + email.getEmailRefcode());
					                }
								}
							//}
							/*else {
							System.out.println("-----check-----");
									System.out.println("EMAIL AFTER: " + emailValue);
									System.out.println("REFCODE AFTER: " + refcode);
									System.out.println("Name AFTER: " + nameValue);
									System.out.println("LN AFTER: " + LNValue);
									System.out.println("AMOUNT AFTER: " + amount);
									System.out.println("DATE AFTER: " + date);
									donor = dservice.findDonorbyEmail(emailValue);
									System.out.println("DONOR: " + dservice.findDonorbyEmail(emailValue));*/
					    	       /*if (dservice.findDonorbyEmail(emailValue) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorbyEmail(emailValue);
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }*/
								//}
							}
		    	        }

	            }
		}
  }
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    List<Donor> donors = null;
    List<Emails> emails = null;
    List<Donation> donations = null;
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    
    public void exporter(List<Donor> donors, HttpServletResponse response) throws IOException{
        this.donors = donors;
        workbook = new XSSFWorkbook();
        DataFormatter dataFormatter = new DataFormatter();
        
        //write header lines
        sheet = workbook.createSheet("Users");
        
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Id", style); 
        createCell(row, 1, "Donor email", style); 
        createCell(row, 2, "First Name", style); 
        createCell(row, 3, "Last Name", style); 
        createCell(row, 4, "Most recent donation date", style); 
        createCell(row, 5, "Most recent donation amount", style); 
        createCell(row, 6, "Average", style);
        
        //write data lines
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyfont = workbook.createFont();
        bodyfont.setBold(false);
        bodyfont.setFontHeight(14);
        bodyStyle.setFont(bodyfont);
                 
        for (int i = 0; i < donors.size(); i++) {
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            createCell(row, columnCount++, String.valueOf(donors.get(i).getId()), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getDonorEmail(), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getDonorFirstName(), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getDonorLastName(), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getRecentDateFormatted(), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getDonorRecentAmountFormatted(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getDonordata().getDonoraverage()), bodyStyle);
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
    public void Emailexporter(List<Emails> emails, HttpServletResponse response) throws IOException{
        this.emails = emails;
        workbook = new XSSFWorkbook();
        DataFormatter dataFormatter = new DataFormatter();
        
        //write header lines
        sheet = workbook.createSheet("Emails");
        
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Id", style); 
        createCell(row, 1, "Email", style); 
        createCell(row, 2, "Refcode", style); 
        createCell(row, 3, "Send Date", style); 
        createCell(row, 4, "Revenue", style); 
        createCell(row, 5, "Average donation", style); 
        createCell(row, 6, "Number of donations", style);
        createCell(row, 7, "Number of donors", style);
        
        //write data lines
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyfont = workbook.createFont();
        bodyfont.setBold(false);
        bodyfont.setFontHeight(14);
        bodyStyle.setFont(bodyfont);
                 
        for (int i = 0; i < emails.size(); i++) {
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            createCell(row, columnCount++, String.valueOf(emails.get(i).getId()), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailName(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailRefcode(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailDateFormatted(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getEmailsum()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getEmailAverageFormatted()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getDonationcount()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getDonorcount()), bodyStyle);
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
    public void Donationexporter(List<Donation> donations, HttpServletResponse response) throws IOException{
        this.donations = donations;
        workbook = new XSSFWorkbook();
        DataFormatter dataFormatter = new DataFormatter();
        
        //write header lines
        sheet = workbook.createSheet("Emails");
        
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Donation Id", style); 
        createCell(row, 1, "Amount", style); 
        createCell(row, 2, "Date", style); 
        createCell(row, 3, "Donor Email", style); 
        createCell(row, 4, "Donor First Name", style); 
        createCell(row, 5, "Donor Last Name", style); 
        createCell(row, 6, "Donor Id", style);
        createCell(row, 7, "Refcode", style);
        
        //write data lines
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyfont = workbook.createFont();
        bodyfont.setBold(false);
        bodyfont.setFontHeight(14);
        bodyStyle.setFont(bodyfont);
                 
        for (int i = 0; i < donations.size(); i++) {
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            createCell(row, columnCount++, String.valueOf(donations.get(i).getId()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donations.get(i).getAmount()), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonationDateFormatted(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorEmail(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorFirstName(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorLastName(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donations.get(i).getDonor().getId()), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getEmailDonation().getEmailRefcode(), bodyStyle);
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
}
