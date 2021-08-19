package com.coding.LojoFundrasing.Util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
import com.coding.LojoFundrasing.Models.Contenttest;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.EmailGroup;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.User;
import com.coding.LojoFundrasing.Models.test;
import com.coding.LojoFundrasing.Services.ContentTestService;
import com.coding.LojoFundrasing.Services.DonationService;
import com.coding.LojoFundrasing.Services.DonorService;
import com.coding.LojoFundrasing.Services.EmailService;
import com.coding.LojoFundrasing.Services.TestService;
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
	@Autowired
	private ContentTestService ctservice;
	@Autowired
	private TestService tservice;
	
	public String getRateFormatted(Double number) {
		if (number == null) {
			number = 0.0;
		}
		double number1 = (double) number;
		DecimalFormat df = new DecimalFormat("0.00000");
		String numberfinal = df.format(number1); 
		return numberfinal;
	}
	
	private String dateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
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
			Emails email = null;
			List<Emails> emails = null;
        	List<Committees> committees = null;
        	List<Donation> donations = null;
        	List<Donation> emaildonations = null;
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
										System.out.println("date value " + dateValue1);
										if (dateValue1.contains("/")) {
											date = new SimpleDateFormat("MM/dd/yy HH:mm").parse(dateValue1);
											DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
											SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											String strDate = dt.format(date);
											date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
											System.out.println("Simple date: " + date);
										}
										else if(dateValue1.contains("-")) {
											date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										}
										//date = new SimpleDateFormat("MM/dd/YY").parse(dateValue1);
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
										System.out.println("RECURRING AFTER: " + Recurring);
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
					    	        	System.out.println("donation recurring " + donation.getRecurring());
					    	        	donation.setDondate(date);
					    	        	donation.setDonation_uploader(uploader);
					    	        	donation.setDonor(dservice.findDonorByIdandCommittee(id, committee.getId()));
					    	        	donation.setCommittee(committee);
					    	        	donations = committee.getDonations();
					    	        	donations.add(donation);
					    	        	committee.setDonations(donations);
					    	        	Emails emaildonation = eservice.findEmailbyRefcodeandCommittee(refcode, committee);
					    	        	if (emaildonation == null){
					    	        		String undate1 = "0001-01-01 01:01";
					    	        		Date undate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(undate1);
					    	        		email = new Emails();
						    	        	email.setEmailName(null);
						    	        	email.setEmaildate(undate);
						    	        	email.setEmailRefcode(refcode);
						    	        	email.setBounces(null);
						    	        	email.setClicks(null);
						    	        	email.setOpeners(null);
						    	        	email.setRecipients(null);
						    	        	email.setUnsubscribers(null);
						    	        	email.setExcludedList(null);
						    	        	email.setList(null);
						    	        	email.setEmail_uploader(uploader);
						    	        	email.setCommittee(committee);
						    	        	emails = committee.getEmails();
						    	        	emails.add(email);
						    	        	committee.setEmails(emails);
						    	        	eservice.createEmail(email);
						    	        	String tempname = "Null" + email.getId();
						    	        	email.setEmailName(tempname);
						    	        	System.out.println("TEMP NAME: " + tempname);
						    	        	eservice.createEmail(email);
						    	        	donation.setEmailDonation(email);
					    	        	}
					    	        	else {
					    	        		donation.setEmailDonation(eservice.findEmailbyRefcodeandCommittee(refcode, committee));
					    	        	}
					    	        	System.out.println("committee after: " + committee.getCommitteeName());
					    	        	//committees.add(committee);
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("CREATE DONATION 2: ");
					    	    		email = donation.getEmailDonation();
					    	    		System.out.println("email: " + email);
					    	    		dservice.getDonorData(donor, committee.getId());
					    	    		//System.out.println("donordata id: " + donor.getDonordata().getId());
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    	        	Recurring = null;
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
					    	        	System.out.println("RECURRING SET: " + Recurring);
					    	        	donation.setRecurring(Recurring);
					    	        	donation.setAmount(amount);
					    	        	System.out.println("amount");
					    	        	donation.setDondate(date);
					    	        	System.out.println("date");
					    	        	donation.setDonor(dservice.findDonorByIdandCommittee(id, committee.getId()));
					    	        	System.out.println("donor");
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
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	System.out.println("create donation");
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("create donation");
					    	        	System.out.println("RECURRING END: " + donation.getRecurring());
					    	        	Emails emaildonation = eservice.findEmailbyRefcodeandCommittee(refcode, committee);
					    	        	if (emaildonation == null){
					    	        		String undate1 = "0001-01-01 01:01";
					    	        		Date undate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(undate1);
					    	        		email = new Emails();
						    	        	email.setEmailName(null);
						    	        	email.setEmaildate(undate);
						    	        	email.setEmailRefcode(refcode);
						    	        	email.setBounces(null);
						    	        	email.setClicks(null);
						    	        	email.setOpeners(null);
						    	        	email.setRecipients(null);
						    	        	email.setUnsubscribers(null);
						    	        	email.setExcludedList(null);
						    	        	email.setList(null);
						    	        	email.setEmail_uploader(uploader);
						    	        	email.setCommittee(committee);
						    	        	emails = committee.getEmails();
						    	        	emails.add(email);
						    	        	committee.setEmails(emails);
						    	        	eservice.createEmail(email);
						    	        	String tempname = "Null" + email.getId();
						    	        	email.setEmailName(tempname);
						    	        	System.out.println("TEMP NAME: " + tempname);
						    	        	eservice.createEmail(email);
						    	        	donation.setEmailDonation(email);
					    	        	}
					    	        	else {
					    	        		donation.setEmailDonation(eservice.findEmailbyRefcodeandCommittee(refcode, committee));
					    	        	}
					    	        	System.out.println("CREATE DONATION 2: ");
					    	        	donservice.createDonation(donation);
					    	    		email = donation.getEmailDonation();
					    	    		System.out.println("Email: " + email.getEmailName());
					    	    		dservice.getDonorData(donor, committee.getId());
					    	    		//System.out.println("donordata id: " + donor.getDonordata().getId());
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    	        	Recurring = null;
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }
									}
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
			int listColumn = 0;
			int excludeColumn = 0;
			int openersColumn = 0;
			int bouncesColumn = 0;
			int unsubscribersColumn = 0;
			int clicksColumn = 0;
			int recipientsColumn = 0;
			String recipientList = null;
			String excludedList = null;
			Long openers = null;
			Long bounces = null;
			Long unsubscribers = null;
			Long clicks = null;
			Long recipients = null;
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
							if (headerValue.contains("RECIPIENT LIST")) {
								listColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("EXCLUDED LIST")) {
								excludeColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("OPEN")) {
								openersColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("UNSUBSCRIBE")) {
								unsubscribersColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("CLICK")) {
								clicksColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("RECIPIENTS")) {
								recipientsColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							if (headerValue.contains("BOUNCE")) {
								bouncesColumn = header.getColumnIndex();
								System.out.println(headerValue);
							}
							System.out.println("Headers: " + headers);
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										System.out.println(nameValue);
									}
									if (cell.getColumnIndex() == clicksColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										clicks = Long.parseLong(amount1); 
										System.out.println(clicks);
									}
									if (cell.getColumnIndex() == recipientsColumn ) {
										String amount1 = dataFormatter.formatCellValue(cell);
										recipients = Long.parseLong(amount1); 
										System.out.println(recipients);
									}
									if (cell.getColumnIndex() == unsubscribersColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										unsubscribers = Long.parseLong(amount1); 
										System.out.println(unsubscribers);
									}
									if (cell.getColumnIndex() == openersColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										openers = Long.parseLong(amount1); 
										System.out.println(openers);
									}
									if (cell.getColumnIndex() == bouncesColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										bounces = Long.parseLong(amount1); 
										System.out.println(bounces);
									}
									if (cell.getColumnIndex() == excludeColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										excludedList = dataFormatter.formatCellValue(cell);
										System.out.println(recipientList);
									}
									if (cell.getColumnIndex() == listColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										recipientList = dataFormatter.formatCellValue(cell);
										System.out.println(recipientList);
									}
									else if (cell.getColumnIndex() == DateColumn) {
										//date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										//date = new SimpleDateFormat("MM/dd//YY").parse(dateValue1);
										//String date = 
										String dateValue1 = dataFormatter.formatCellValue(cell);
										System.out.println(dateValue1);
										//date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										date = new SimpleDateFormat("MM/dd/yy").parse(dateValue1);
										System.out.println("Simple date: " + date);
										//date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										//System.out.println(dateValue1);
										//System.out.println("Simple date: " + date);
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
					    	        	email.setBounces(bounces);
					    	        	email.setClicks(clicks);
					    	        	email.setOpeners(openers);
					    	        	email.setRecipients(recipients);
					    	        	email.setUnsubscribers(unsubscribers);
					    	        	email.setExcludedList(excludedList);
					    	        	email.setList(recipientList);
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
					    	        	System.out.println("found email");
					    	        	email.setEmailName(nameValue);
					    	        	email.setEmaildate(date);
					    	        	email.setEmailRefcode(refcode);
					    	        	email.setBounces(bounces);
					    	        	email.setClicks(clicks);
					    	        	email.setOpeners(openers);
					    	        	email.setRecipients(recipients);
					    	        	email.setUnsubscribers(unsubscribers);
					    	        	email.setExcludedList(excludedList);
					    	        	email.setList(recipientList);
					    	        	email.setEmail_uploader(uploader);
					    	        	email.setCommittee(committee);
					    	        	emails = committee.getEmails();
					    	        	emails.add(email);
					    	        	committee.setEmails(emails);
					    	        	eservice.createEmail(email);
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    				System.out.println("Id: " + email.getId() + " Email: " + email.getEmailRefcode());
					                }
								}
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
        sheet = workbook.createSheet("Donors");
        
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
        createCell(row, 7, "Highest previous contribution", style);
        createCell(row, 8, "Donations", style); 
        createCell(row, 9, "Total given", style); 
        createCell(row, 10, "Most recent donation in range date", style); 
        createCell(row, 11, "Most recent donation in range amount", style);
        createCell(row, 12, "Average within range", style); 
        createCell(row, 13, "HPC within range", style);
        createCell(row, 14, "Count within range", style);
        createCell(row, 15, "Total given within range", style);
        
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
            createCell(row, columnCount++, String.valueOf(donors.get(i).getDonordata().getHpc()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getDonordata().getDonor_contributioncount()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getDonordata().getDonorsum()), bodyStyle);
            createCell(row, columnCount++, donors.get(i).getRecentDateinRangeFormatted(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getMostrecentInrangeAmount()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getAveragewithinrange()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getHpcwithinrange()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getCountwithinrange()), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donors.get(i).getSumwithinrange()), bodyStyle);
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
    public void Emailexporter(List<Emails> emails, List<String> input, HttpServletResponse response) throws IOException{
        //column values
    	int ClickCol = 0;
    	int OpenCol = 0;
    	int BounceCol = 0;
    	int UnsubCol = 0;
    	int ClickrateCol = 0;
    	int OpenrateCol = 0;
    	int UnsubrateCol = 0;
    	int BouncerateCol = 0;
    	int ClickOpenCol = 0;
    	int RevCol = 0;
    	int DonationsCol = 0;
    	int DonorsCol =0;
    	int AvCol = 0;
    	int DonOpenCol = 0;
    	int DonClickCol = 0;
    	int DonorsOpenCol =0;
    	int DonorsClickCol = 0;
    	int DonRecurCol = 0;
    	int DonorsRecurCol =0;
    	int RevRecurCol = 0;
    	
    	
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
        createCell(row, 4, "List", style); 
        createCell(row, 5, "Excluded List", style); 
        createCell(row, 6, "Recipients", style);
        
        int columnCount = 7;
        Cell cell = row.createCell(columnCount);
        
            for (int i = 0; i < input.size(); i++) {
            	System.out.println("Input: " + input.get(i));
            	if (input.get(i).equals("Clicks")) {
                    ClickCol = columnCount;
                    createCell(row, columnCount++, "Clicks", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickCol);
            	}
            	if (input.get(i).equals("Opens")) {
            		OpenCol = columnCount;
                    createCell(row, columnCount++, "Opens", style);
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + OpenCol);
            	}
            	if (input.get(i).equals("Bounces")) {
                    BounceCol = columnCount;
                    createCell(row, columnCount++, "Bounces", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + BounceCol);
            	}
            	if (input.get(i).equals("Unsubscribes")) {
            		UnsubCol = columnCount;
                    createCell(row, columnCount++, "Unsubscribes", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + UnsubCol);
            	}
            	if (input.get(i).equals("Open rate")) {
            		OpenrateCol = columnCount;
                    createCell(row, columnCount++, "Open rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + OpenrateCol);
            	}
            	if (input.get(i).equals("Click rate")) {
            		ClickrateCol = columnCount;
                    createCell(row, columnCount++, "Click rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickrateCol);
            	}
            	if (input.get(i).equals("Unsubscribe rate")) {
            		UnsubrateCol = columnCount;
                    createCell(row, columnCount++, "Unsubscribe rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + UnsubrateCol);
            	}
            	if (input.get(i).equals("Bounce rate")) {
            		BouncerateCol = columnCount;
                    createCell(row, columnCount++, "Bounce rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + BouncerateCol);
            	}
            	if (input.get(i).equals("Clicks/opens")) {
            		ClickOpenCol = columnCount;
                    createCell(row, columnCount++, "Clicks per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickOpenCol);
            	}
            	if (input.get(i).equals("Revenue")) {
            		RevCol = columnCount;
                    createCell(row, columnCount++, "Revenue", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + RevCol);
            	}
            	if (input.get(i).equals("Donations")) {
            		DonationsCol = columnCount;
                    createCell(row, columnCount++, "Donations", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonationsCol);
            	}
            	if (input.get(i).equals("Donors")) {
            		DonorsCol = columnCount;
                    createCell(row, columnCount++, "Donors", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsCol);
            	}
            	if (input.get(i).equals("Average donation")) {
            		AvCol = columnCount;
                    createCell(row, columnCount++, "Average donation", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + AvCol);
            	}
            	if (input.get(i).equals("Donations/open")) {
            		DonOpenCol = columnCount;
                    createCell(row, columnCount++, "Donations per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonOpenCol);
            	}
            	if (input.get(i).equals("Donations/click")) {
            		DonClickCol = columnCount;
                    createCell(row, columnCount++, "Donations per click", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonClickCol);
            	}
            	if (input.get(i).equals("Donors/open")) {
            		DonorsOpenCol = columnCount;
                    createCell(row, columnCount++, "Donors per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsOpenCol);
            	}
            	if (input.get(i).equals("Donors/click")) {
            		DonorsClickCol = columnCount;
                    createCell(row, columnCount++, "Donors per click", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsClickCol);
            	}
            	if (input.get(i).equals("Recurring donations")) {
            		DonRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring donations", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonRecurCol);
            	}
            	if (input.get(i).equals("Recurring donors")) {
            		DonorsRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring donors", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsRecurCol);
            	}
            	if (input.get(i).equals("Recurring revenue")) {
            		RevRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring revenue", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + RevRecurCol);
            	}
            }
        
        //write data lines
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyfont = workbook.createFont();
        bodyfont.setBold(false);
        bodyfont.setFontHeight(14);
        bodyStyle.setFont(bodyfont);
                 
        for (int i = 0; i < emails.size(); i++) {
            row = sheet.createRow(rowCount++);
            columnCount = 0;
            createCell(row, columnCount++, String.valueOf(emails.get(i).getId()), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailName(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailRefcode(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getEmailDateFormatted(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getList(), bodyStyle);
            createCell(row, columnCount++, emails.get(i).getExcludedList(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emails.get(i).getRecipients()), bodyStyle);
            if (columnCount == ClickCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getClicks()), bodyStyle);
            }
            if (columnCount == OpenCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getOpeners()), bodyStyle);
            }
            if (columnCount == BounceCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getBounces()), bodyStyle);
            }
            if (columnCount == UnsubCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getUnsubscribers()), bodyStyle);
            }
            if (columnCount == OpenrateCol) {
                createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getOpenRate()), bodyStyle);
            }
            if (columnCount == ClickrateCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getClickRate()), bodyStyle);
            }
            if (columnCount == UnsubrateCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getUnsubscribeRate()), bodyStyle);
            }
            if (columnCount == BouncerateCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getBounceRate()), bodyStyle);
            }
            if (columnCount == ClickOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getClicksOpens()), bodyStyle);
            }
            if (columnCount == RevCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getEmailsum()), bodyStyle);
            }
            if (columnCount == DonationsCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getDonationcount()), bodyStyle);
            }
            if (columnCount == DonorsCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getDonorcount()), bodyStyle);
            }
            if (columnCount == AvCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getEmaildata().getEmailAverageFormatted()), bodyStyle);
            }
            if (columnCount == DonOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getDonationsOpens()), bodyStyle);
            }
            if (columnCount == DonClickCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getDonationsClicks()), bodyStyle);
            }
            if (columnCount == DonorsOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getDonorsOpens()), bodyStyle);
            }
            if (columnCount == DonorsClickCol) {
            	createCell(row, columnCount++, getRateFormatted(emails.get(i).getEmaildata().getDonorsClicks()), bodyStyle);
            }
            if (columnCount == DonRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getRecurringDonationCount()), bodyStyle);
            }
            if (columnCount == DonorsRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getRecurringDonorCount()), bodyStyle);
            }
            if (columnCount == RevRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emails.get(i).getRecurringRevenue()), bodyStyle);
            }
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
    public void EmailGroupexporter(List<EmailGroup> emailgroup, List<String> input, HttpServletResponse response) throws IOException{
        //column values
    	int ClickCol = 0;
    	int OpenCol = 0;
    	int BounceCol = 0;
    	int UnsubCol = 0;
    	int ClickrateCol = 0;
    	int OpenrateCol = 0;
    	int UnsubrateCol = 0;
    	int BouncerateCol = 0;
    	int ClickOpenCol = 0;
    	int RevCol = 0;
    	int DonationsCol = 0;
    	int DonorsCol =0;
    	int AvCol = 0;
    	int DonOpenCol = 0;
    	int DonClickCol = 0;
    	int DonorsOpenCol =0;
    	int DonorsClickCol = 0;
    	int DonRecurCol = 0;
    	int DonorsRecurCol =0;
    	int RevRecurCol = 0;
    	
    	
    	//this.emailgroup = emailgroup;
        workbook = new XSSFWorkbook();
        DataFormatter dataFormatter = new DataFormatter();
        
        //write header lines
        sheet = workbook.createSheet("Email Groups");
        
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Id", style); 
        createCell(row, 1, "Email Group", style); 
        createCell(row, 2, "Recipients", style);
        
        int columnCount = 3;
        Cell cell = row.createCell(columnCount);
        
            for (int i = 0; i < input.size(); i++) {
            	System.out.println("Input: " + input.get(i));
            	if (input.get(i).equals("Clicks")) {
                    ClickCol = columnCount;
                    createCell(row, columnCount++, "Clicks", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickCol);
            	}
            	if (input.get(i).equals("Opens")) {
            		OpenCol = columnCount;
                    createCell(row, columnCount++, "Opens", style);
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + OpenCol);
            	}
            	if (input.get(i).equals("Bounces")) {
                    BounceCol = columnCount;
                    createCell(row, columnCount++, "Bounces", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + BounceCol);
            	}
            	if (input.get(i).equals("Unsubscribes")) {
            		UnsubCol = columnCount;
                    createCell(row, columnCount++, "Unsubscribes", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + UnsubCol);
            	}
            	if (input.get(i).equals("Open rate")) {
            		OpenrateCol = columnCount;
                    createCell(row, columnCount++, "Open rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + OpenrateCol);
            	}
            	if (input.get(i).equals("Click rate")) {
            		ClickrateCol = columnCount;
                    createCell(row, columnCount++, "Click rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickrateCol);
            	}
            	if (input.get(i).equals("Unsubscribe rate")) {
            		UnsubrateCol = columnCount;
                    createCell(row, columnCount++, "Unsubscribe rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + UnsubrateCol);
            	}
            	if (input.get(i).equals("Bounce rate")) {
            		BouncerateCol = columnCount;
                    createCell(row, columnCount++, "Bounce rate", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + BouncerateCol);
            	}
            	if (input.get(i).equals("Clicks/opens")) {
            		ClickOpenCol = columnCount;
                    createCell(row, columnCount++, "Clicks per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + ClickOpenCol);
            	}
            	if (input.get(i).equals("Revenue")) {
            		RevCol = columnCount;
                    createCell(row, columnCount++, "Revenue", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + RevCol);
            	}
            	if (input.get(i).equals("Donations")) {
            		DonationsCol = columnCount;
                    createCell(row, columnCount++, "Donations", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonationsCol);
            	}
            	if (input.get(i).equals("Donors")) {
            		DonorsCol = columnCount;
                    createCell(row, columnCount++, "Donors", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsCol);
            	}
            	if (input.get(i).equals("Average donation")) {
            		AvCol = columnCount;
                    createCell(row, columnCount++, "Average donation", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + AvCol);
            	}
            	if (input.get(i).equals("Donations/open")) {
            		DonOpenCol = columnCount;
                    createCell(row, columnCount++, "Donations per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonOpenCol);
            	}
            	if (input.get(i).equals("Donations/click")) {
            		DonClickCol = columnCount;
                    createCell(row, columnCount++, "Donations per click", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonClickCol);
            	}
            	if (input.get(i).equals("Donors/open")) {
            		DonorsOpenCol = columnCount;
                    createCell(row, columnCount++, "Donors per open", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsOpenCol);
            	}
            	if (input.get(i).equals("Donors/click")) {
            		DonorsClickCol = columnCount;
                    createCell(row, columnCount++, "Donors per click", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsClickCol);
            	}
            	if (input.get(i).equals("Recurring donations")) {
            		DonRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring donations", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonRecurCol);
            	}
            	if (input.get(i).equals("Recurring donors")) {
            		DonorsRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring donors", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + DonorsRecurCol);
            	}
            	if (input.get(i).equals("Recurring revenue")) {
            		RevRecurCol = columnCount;
                    createCell(row, columnCount++, "Recurring revenue", style); 
                    System.out.println("Input 2: " + input.get(i));
                    System.out.println("Column logged: " + RevRecurCol);
            	}
            }
        
        //write data lines
        int rowCount = 1;
        CellStyle bodyStyle = workbook.createCellStyle();
        XSSFFont bodyfont = workbook.createFont();
        bodyfont.setBold(false);
        bodyfont.setFontHeight(14);
        bodyStyle.setFont(bodyfont);
                 
        for (int i = 0; i < emailgroup.size(); i++) {
            row = sheet.createRow(rowCount++);
            columnCount = 0;
            createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getId()), bodyStyle);
            createCell(row, columnCount++, emailgroup.get(i).getEmailgroupName(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupRecipients()), bodyStyle);
            if (columnCount == ClickCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupClicks()), bodyStyle);
            }
            if (columnCount == OpenCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupOpeners()), bodyStyle);
            }
            if (columnCount == BounceCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupBounces()), bodyStyle);
            }
            if (columnCount == UnsubCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupUnsubscribers()), bodyStyle);
            }
            if (columnCount == OpenrateCol) {
                createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupopenRate()), bodyStyle);
            }
            if (columnCount == ClickrateCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupclickRate()), bodyStyle);
            }
            if (columnCount == UnsubrateCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupunsubscribeRate()), bodyStyle);
            }
            if (columnCount == BouncerateCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupbounceRate()), bodyStyle);
            }
            if (columnCount == ClickOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupclicksOpens()), bodyStyle);
            }
            if (columnCount == RevCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupsum()), bodyStyle);
            }
            if (columnCount == DonationsCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupdonationcount()), bodyStyle);
            }
            if (columnCount == DonorsCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupdonorcount()), bodyStyle);
            }
            if (columnCount == AvCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupaverage()), bodyStyle);
            }
            if (columnCount == DonOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupdonationsOpens()), bodyStyle);
            }
            if (columnCount == DonClickCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupdonationsClicks()), bodyStyle);
            }
            if (columnCount == DonorsOpenCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupdonorsOpens()), bodyStyle);
            }
            if (columnCount == DonorsClickCol) {
            	createCell(row, columnCount++, getRateFormatted(emailgroup.get(i).getGroupdonorsClicks()), bodyStyle);
            }
            if (columnCount == DonRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupRecurringDonationCount()), bodyStyle);
            }
            if (columnCount == DonorsRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupRecurringDonorCount()), bodyStyle);
            }
            if (columnCount == RevRecurCol) {
            	createCell(row, columnCount++, String.valueOf(emailgroup.get(i).getGroupRecurringRevenue()), bodyStyle);
            }
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
        createCell(row, 3, "Refcode", style);
        createCell(row, 4, "Donor Email", style); 
        createCell(row, 5, "Donor First Name", style); 
        createCell(row, 6, "Donor Last Name", style); 
        createCell(row, 7, "Donor Id", style);
        createCell(row, 8, "Donor Address", style); 
        createCell(row, 9, "Donor City", style);
        createCell(row, 10, "Donor State", style); 
        createCell(row, 11, "Donor Zipcode", style); 
        createCell(row, 12, "Donor Country", style); 
        createCell(row, 13, "Donor Phone", style);
        
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
            createCell(row, columnCount++, donations.get(i).getEmailDonation().getEmailRefcode(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorEmail(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorFirstName(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getDonorLastName(), bodyStyle);
            createCell(row, columnCount++, String.valueOf(donations.get(i).getDonor().getId()), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getAddress(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getCity(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getState(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getZipcode(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getCountry(), bodyStyle);
            createCell(row, columnCount++, donations.get(i).getDonor().getPhone(), bodyStyle);
        }
        //export
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
	}
    
	public void readExcelSheetTest(String excelPath, Long user_id, Committees committee)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		List<String> list = new ArrayList<String>();

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));
		System.out.println("workbook created");

		int x = workbook.getNumberOfSheets();
		
		System.out.println("number of sheets " + x);
		
		System.out.println("READ TEST EXCEL SHEET");

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
			int dateColumn = 0;
			int typeColumn = 0;
			int listColumn = 0;
			int recipientNumberColumn = 0;
			int nameColumn = 0;
			int jtkColumn = 0;
			int topicColumn = 0;
			int testColumn = 0;
			int fulllistColumn = 0;
			int goWinnerColumn = 0;
			int clickrcvColumn = 0;
			int variantAColumn = 0;
			int variantARecipientNumberColumn = 0;
			int variantAOpenRateColumn = 0; 
			int variantAClickRateColumn = 0;
			int variantAOpensColumn = 0;
			int variantAGOColumn = 0;
			int variantBColumn = 0;
			int variantBRecipientNumberColumn = 0;
			int variantBOpenRateColumn = 0; 
			int variantBClickRateColumn = 0;
			int variantBOpensColumn = 0;
			int variantBGOColumn = 0;
			Contenttest contenttest = null;
			test bigtest = null;
			List<Contenttest> content = null;
			User uploader = uservice.findUserbyId(user_id);
			String senddate = null;
			String type = null;
			String RecipientsList = null;
			Long recipients = null;
			String name = null;
			String jtk = null;
			String topic = null;
			String test = null;
			String fullistWinner = null;
			String GoWinner = null;
			String ClickRcvWinner = null;
			String VariantA = null;
			Long ARecipientNumber = null;
			Double AClickRate = null;
			Double AOpenRate = null;
			Long AOpens = null;
			Double AGiftOpens = null;
			String VariantB = null;
			Long BRecipientNumber = null;
			Double BClickRate = null;
			Double BOpenRate = null;
			Long BOpens = null;
			Double BGiftOpens = null;

			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {
	                	Cell cell = cellIterator.next();
	                	//System.out.println("CELL: " + cell.getAddress());
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							
							String headerValue = dataFormatter.formatCellValue(header).toUpperCase();
							if (headerValue.contains("DATE")) {
								dateColumn = header.getColumnIndex();
								System.out.println("date column: " + dateColumn);
							}
							if (headerValue.contains("RS")) {
								typeColumn = header.getColumnIndex();
							}
							if (headerValue.contains("RECIPIENTS LIST")) {
								listColumn  = header.getColumnIndex();
								System.out.println("RECIPIENTS LIST: " + headerValue);
							}
							if (headerValue.contains("TOTAL NUMBER OF RECIPIENTS")) {
								recipientNumberColumn = header.getColumnIndex();
								System.out.println("RECIPIENTS NUMBER: " + headerValue);
							}
							if (headerValue.contains("(ACOUSTIC NAME)")) {
								nameColumn = header.getColumnIndex();
							}
							if (headerValue.contains("JTK")) {
								jtkColumn = header.getColumnIndex();
							}
							if (headerValue.contains("TOPIC")) {
								topicColumn = header.getColumnIndex();
							}
							if (headerValue.contains("TESTING FACTOR")) {
								testColumn = header.getColumnIndex();
							}
							if (headerValue.contains("FULL LIST")) {
								fulllistColumn = header.getColumnIndex();
							}
							if (headerValue.contains("G/O WINNER")) {
								goWinnerColumn = header.getColumnIndex();
							}
							if (headerValue.contains("CLK/RCV WINNER")) {
								clickrcvColumn = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT A TEST")) {
								variantAColumn = header.getColumnIndex();
								System.out.println("VARIANT A: " + variantAColumn);
							}
							if (headerValue.contains("VARIANT A RECIPIENT NUMBER")) {
								variantARecipientNumberColumn  = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT A OPEN RATE (OPN/RCV)")) {
								variantAOpenRateColumn  = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT A CLICK RATE")) {
								variantAClickRateColumn  = header.getColumnIndex();
								System.out.println("variantAClickRateColumn: " + variantAClickRateColumn );
							}
							if (headerValue.contains("VARIANT A OPENS")) {
								variantAOpensColumn = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT A GIFT/OPEN")) {
								variantAGOColumn  = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT B TEST")) {
								variantBColumn = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT B RECIPIENT NUMBER")) {
								variantBRecipientNumberColumn = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT B OPEN RATE (OPN/RCV)")) {
								variantBOpenRateColumn  = header.getColumnIndex();
								System.out.println("variantBOpenRateColumn: " + variantBOpenRateColumn);
							}
							if (headerValue.contains("VARIANT B CLICK RATE")) {
								variantBClickRateColumn  = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT B OPENS")) {
								variantBOpensColumn  = header.getColumnIndex();
							}
							if (headerValue.contains("VARIANT B GIFT/OPEN")) {
								variantBGOColumn  = header.getColumnIndex();
								System.out.println("VARIANT B GIFT/OPEN: " + headerValue);
							}
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									//System.out.println("CELL: " + cell);
									//System.out.println("CELL COLUMN: " + cell.getColumnIndex());
									if (cell.getColumnIndex() == jtkColumn) {
										jtk = dataFormatter.formatCellValue(cell);
										System.out.println("jtk:" + jtk);
									}
									else if (cell.getColumnIndex() == listColumn) {
										RecipientsList = dataFormatter.formatCellValue(cell);
										System.out.println(RecipientsList);
									}
									else if (cell.getColumnIndex() == recipientNumberColumn) {
										String recipients1 = dataFormatter.formatCellValue(cell);
										recipients = Long.parseLong(recipients1);
										System.out.println(recipients1);
									}
									else if (cell.getColumnIndex() == typeColumn) {
										type = dataFormatter.formatCellValue(cell);
									}
									else if (cell.getColumnIndex() == nameColumn) {
										name = dataFormatter.formatCellValue(cell);
									}
									else if (cell.getColumnIndex() == dateColumn) {
										senddate = dataFormatter.formatCellValue(cell);
									}
									else if (cell.getColumnIndex() == topicColumn) {
										topic = dataFormatter.formatCellValue(cell);
										System.out.println("topic: " + topic);
									}
									else if (cell.getColumnIndex() == testColumn) {
										test = dataFormatter.formatCellValue(cell);
										System.out.println("test: " + test);
									}
									else if (cell.getColumnIndex() == fulllistColumn) {
										fullistWinner = dataFormatter.formatCellValue(cell);
										System.out.println("fullistWinner: " + fullistWinner);
									}
									else if (cell.getColumnIndex() == goWinnerColumn) {
										GoWinner = dataFormatter.formatCellValue(cell);
										System.out.println("GoWinner : " + GoWinner);
									}
									else if (cell.getColumnIndex() == clickrcvColumn) {
										ClickRcvWinner  = dataFormatter.formatCellValue(cell);
										System.out.println("ClickRcvWinner: " + ClickRcvWinner);
									}
									else if (cell.getColumnIndex() == variantAColumn) {
										VariantA  = dataFormatter.formatCellValue(cell);
										System.out.println("VariantA : " + VariantA);
									}
									else if (cell.getColumnIndex() == variantARecipientNumberColumn) {
										String ARecipientNumber1 = dataFormatter.formatCellValue(cell);
										ARecipientNumber = Long.parseLong(ARecipientNumber1);
										System.out.println("ARecipientNumber: " + ARecipientNumber);
									}
									else if (cell.getColumnIndex() == variantAOpenRateColumn) {
										String AOpenRate1 = dataFormatter.formatCellValue(cell);
										AOpenRate = Double.parseDouble(AOpenRate1);
										System.out.println("AOpenRate: " + AOpenRate);
									}
									else if (cell.getColumnIndex() == variantAClickRateColumn) {
										String AClickRate1 = dataFormatter.formatCellValue(cell);
										System.out.println("AClickRate1: " + AClickRate1);
										AClickRate = Double.parseDouble(AClickRate1);
										System.out.println("AClickRate: " + AClickRate);
									}
									else if (cell.getColumnIndex() == variantAOpensColumn) {
										String AOpens1 = dataFormatter.formatCellValue(cell);
										AOpens = Long.parseLong(AOpens1);
										System.out.println("AOpens: " + AOpens);
									}
									else if (cell.getColumnIndex() == variantAGOColumn) {
										String AGiftOpens1 = dataFormatter.formatCellValue(cell);
										AGiftOpens = Double.parseDouble(AGiftOpens1);
										System.out.println("AGiftOpens: " + AGiftOpens);
									}
									else if (cell.getColumnIndex() == variantBColumn) {
										VariantB = dataFormatter.formatCellValue(cell);
										System.out.println("VariantB: " + VariantB);
									}
									else if (cell.getColumnIndex() == variantBRecipientNumberColumn) {
										String BRecipientNumber1  = dataFormatter.formatCellValue(cell);
										BRecipientNumber = Long.parseLong(BRecipientNumber1);
										System.out.println("BRecipientNumber: " + BRecipientNumber);
									}
									else if (cell.getColumnIndex() == variantBOpenRateColumn) {
										System.out.println("cell: " + cell);
										String BOpenRate1 = dataFormatter.formatCellValue(cell);
										System.out.println("BOpenRate1: " + BOpenRate1);
										BOpenRate = Double.parseDouble(BOpenRate1);
										System.out.println("BOpenRate: " + BOpenRate);
									}
									else if (cell.getColumnIndex() == variantBClickRateColumn) {
										System.out.println("BClickRate: " + cell);
										String BClickRate1 = dataFormatter.formatCellValue(cell);
										BClickRate = Double.parseDouble(BClickRate1);
										System.out.println("BClickRate: " + BClickRate);
									}
									else if (cell.getColumnIndex() == variantBOpensColumn) {
										String BOpens1 = dataFormatter.formatCellValue(cell);
										BOpens = Long.parseLong(BOpens1);
										System.out.println("BOpens: " + BOpens);
									}
									else if (cell.getColumnIndex() == variantBGOColumn) {
										String BGiftOpens1 = dataFormatter.formatCellValue(cell);
										BGiftOpens  = Double.parseDouble(BGiftOpens1);
							    	   if (ctservice.findContentTestbyListCommitteeJtk(RecipientsList, jtk, committee.getId()) == null) {
							    		   contenttest = new Contenttest();
							    		   contenttest.setAClickRate(AClickRate);
							    		   contenttest.setSenddate(senddate);
							    		   contenttest.setType(type);
							    		   contenttest.setTest(test);
							    		   contenttest.setTopic(topic);
							    		   contenttest.setRecipientsList(RecipientsList);
							    		   contenttest.setRecipients(recipients);
							    		   contenttest.setName(name);
							    		   contenttest.setJtk(jtk);
							    		   contenttest.setFullistWinner(fullistWinner);
							    		   contenttest.setGoWinner(GoWinner);
							    		   contenttest.setClickRcvWinner(ClickRcvWinner);
							    		   contenttest.setVariantA(VariantA);
							    		   contenttest.setARecipientNumber(ARecipientNumber);
							    		   contenttest.setAClickRate(AClickRate);
							    		   contenttest.setAOpenRate(AOpenRate);
							    		   contenttest.setAOpens(AOpens);
							    		   contenttest.setAGiftOpens(AGiftOpens);
							    		   contenttest.setVariantB(VariantB);
							    		   contenttest.setBRecipientNumber(BRecipientNumber);
							    		   contenttest.setBClickRate(BClickRate);
							    		   contenttest.setBOpenRate(BOpenRate);
							    		   contenttest.setBOpens(BOpens);
							    		   contenttest.setBGiftOpens(BGiftOpens);
							    		   contenttest.setCommittee(committee);
							    		   if (tservice.findTestByNameandCommittee(test, committee.getId()) == null) {
							    			   bigtest = new test();
							    			   bigtest.setTestcategory(test);
							    			   bigtest.setCommittee(committee);
									    	   tservice.createTest(bigtest);
									    	   contenttest.setBigtest(bigtest);
							    		   }
							    		   else {
							    			   bigtest = tservice.findTestByNameandCommittee(test, committee.getId()); 
							    			   bigtest.setTestcategory(test);
							    			   bigtest.setCommittee(committee);
									    	   tservice.createTest(bigtest);
									    	   contenttest.setBigtest(bigtest);
							    		   }
					    	        	}
					    	        	else {
								    		   contenttest = ctservice.findContentTestbyListCommitteeJtk(RecipientsList, jtk, committee.getId());
								    		   System.out.println("test: " + ctservice.findContentTestbyListCommitteeJtk(RecipientsList, jtk, committee.getId()).getId());
								    		   contenttest.setAClickRate(AClickRate);
								    		   contenttest.setSenddate(senddate);
								    		   contenttest.setType(type);
								    		   contenttest.setTest(test);
								    		   contenttest.setTopic(topic);
								    		   contenttest.setRecipientsList(RecipientsList);
								    		   contenttest.setRecipients(recipients);
								    		   contenttest.setName(name);
								    		   contenttest.setJtk(jtk);
								    		   contenttest.setFullistWinner(fullistWinner);
								    		   contenttest.setGoWinner(GoWinner);
								    		   contenttest.setClickRcvWinner(ClickRcvWinner);
								    		   contenttest.setVariantA(VariantA);
								    		   contenttest.setARecipientNumber(ARecipientNumber);
								    		   contenttest.setAClickRate(AClickRate);
								    		   contenttest.setAOpenRate(AOpenRate);
								    		   contenttest.setAOpens(AOpens);
								    		   contenttest.setAGiftOpens(AGiftOpens);
								    		   contenttest.setVariantB(VariantB);
								    		   contenttest.setBRecipientNumber(BRecipientNumber);
								    		   contenttest.setBClickRate(BClickRate);
								    		   contenttest.setBOpenRate(BOpenRate);
								    		   contenttest.setBOpens(BOpens);
								    		   contenttest.setBGiftOpens(BGiftOpens);
								    		   contenttest.setCommittee(committee);
								    		   if (tservice.findTestByNameandCommittee(test, committee.getId()) == null) {
								    			   bigtest = new test();
								    			   bigtest.setTestcategory(test);
								    			   bigtest.setCommittee(committee);
										    	   tservice.createTest(bigtest);
										    	   contenttest.setBigtest(bigtest);
								    		   }
								    		   else {
								    			   bigtest = tservice.findTestByNameandCommittee(test, committee.getId()); 
								    			   bigtest.setTestcategory(test);
								    			   bigtest.setCommittee(committee);
										    	   tservice.createTest(bigtest);
										    	   contenttest.setBigtest(bigtest);
								    		   }
					    	        	}
							    	   ctservice.createContentTest(contenttest);
							    	   tservice.TestVariables(contenttest);
							    	   /*content = bigtest.getContent();
					    			   bigtest.setContent(content);
					    			   content.add(contenttest);
					    			   bigtest.setContent(content);
							    	   tservice.createTest(bigtest);
					    	        	/*System.out.println("committee after: " + committee.getCommitteeName());
					    	        	//committees.add(committee);
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("CREATE DONATION 2: ");
					    	    		email = donation.getEmailDonation();
					    	    		System.out.println("email: " + email);
					    	    		dservice.getDonorData(donor, committee.getId());
					    	    		//System.out.println("donordata id: " + donor.getDonordata().getId());
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    	        	Recurring = null;
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
					    	        	System.out.println("RECURRING SET: " + Recurring);
					    	        	donation.setRecurring(Recurring);
					    	        	donation.setAmount(amount);
					    	        	System.out.println("amount");
					    	        	donation.setDondate(date);
					    	        	System.out.println("date");
					    	        	donation.setDonor(dservice.findDonorByIdandCommittee(id, committee.getId()));
					    	        	System.out.println("donor");
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
					    	        	System.out.println("UPLOADER FROM DONATION: " + donation.getDonation_uploader().getId());
					    	        	System.out.println("create donation");
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("create donation");
					    	        	System.out.println("RECURRING END: " + donation.getRecurring());
					    	        	Emails emaildonation = eservice.findEmailbyRefcodeandCommittee(refcode, committee);
					    	        	if (emaildonation == null){
					    	        		String undate1 = "0001-01-01 01:01";
					    	        		Date undate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(undate1);
					    	        		email = new Emails();
						    	        	email.setEmailName(null);
						    	        	email.setEmaildate(undate);
						    	        	email.setEmailRefcode(refcode);
						    	        	email.setBounces(null);
						    	        	email.setClicks(null);
						    	        	email.setOpeners(null);
						    	        	email.setRecipients(null);
						    	        	email.setUnsubscribers(null);
						    	        	email.setExcludedList(null);
						    	        	email.setList(null);
						    	        	email.setEmail_uploader(uploader);
						    	        	email.setCommittee(committee);
						    	        	emails = committee.getEmails();
						    	        	emails.add(email);
						    	        	committee.setEmails(emails);
						    	        	eservice.createEmail(email);
						    	        	String tempname = "Null" + email.getId();
						    	        	email.setEmailName(tempname);
						    	        	System.out.println("TEMP NAME: " + tempname);
						    	        	eservice.createEmail(email);
						    	        	donation.setEmailDonation(email);
					    	        	}
					    	        	else {
					    	        		donation.setEmailDonation(eservice.findEmailbyRefcodeandCommittee(refcode, committee));
					    	        	}
					    	        	System.out.println("CREATE DONATION 2: ");
					    	        	donservice.createDonation(donation);
					    	    		email = donation.getEmailDonation();
					    	    		System.out.println("Email: " + email.getEmailName());
					    	    		dservice.getDonorData(donor, committee.getId());
					    	    		//System.out.println("donordata id: " + donor.getDonordata().getId());
					    	    		eservice.getEmailData(email, committee.getId());
					    	        	refcode = null;
					    	        	Recurring = null;
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }
									}*/
							}
		    	        }

	            }
		}
	}
    
}
}
	
