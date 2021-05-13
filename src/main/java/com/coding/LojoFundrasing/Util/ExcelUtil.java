package com.coding.LojoFundrasing.Util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Services.DonorService;

@Component
public class ExcelUtil {
	@Autowired
	private DonorService dservice;
	
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
	
	public void readExcelSheet(String excelPath)
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
			String emailValue = null;
			String nameValue = null;
			String LNValue = null;
			String date = null;
			Donor donor = null;
			Date dateValue = new Date();
			Date timeValue = null;
			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {

	                    Cell cell = cellIterator.next();
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							System.out.println("Header: " + header);
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							System.out.println("Headers: " + headers);
							String headerValue = dataFormatter.formatCellValue(header);
							if (headerValue.contentEquals("FirstName")) {
								NameColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("firstname")) {
								NameColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("LastName")) {
								LastNameColumn = header.getColumnIndex();
								System.out.println("DateColumn: " + LastNameColumn);
							}
							if (headerValue.contentEquals("lastname")) {
								LastNameColumn = header.getColumnIndex();
								System.out.println("DateColumn: " + LastNameColumn);
							}
							if (headerValue.contentEquals("email")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("Email")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("Amount")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("Date")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("Time")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
							if (headerValue.contentEquals("Refcode")) {
								EmailColumn = header.getColumnIndex();
								System.out.println("NameColumn: " + NameColumn);
							}
						}
						else {
							//for (int j = 0; j < headers.size(); j++) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									System.out.println("Value: " + value);
									values.add(value);
									System.out.println("Values: " + values);
									System.out.println("-----header check-----");
									if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										/*System.out.print("map: " + userMap);
								        System.out.print("usermap section");*/
								        /*Set<String> keys = userMap.keySet();
								        System.out.print(keys);
								        for(String key : keys) {
								            System.out.println(key);
								            System.out.println(userMap.get(key));    
								        }*/
									}
									if (cell.getColumnIndex() == EmailColumn) {
										emailValue = dataFormatter.formatCellValue(value);
									}
									if (cell.getColumnIndex() == LastNameColumn) {
										LNValue = dataFormatter.formatCellValue(value);
									}
									/*if (cell.getColumnIndex() == DateColumn) {
										date = dataFormatter.formatCellValue(cell);
										//dateValue = cell.getDateCellValue();
										//String date1 = new SimpleDateFormat("yyyy-MM-dd").format(dateValue);
										//timeValue = new SimpleDateFormat("hh:mm").parse(date);
										System.out.println("STRING DATE: " + date);
										Date dateValue1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(date);
										System.out.println("DATE SIMPLE: " + dateValue1);
										SimpleDateFormat dated = new SimpleDateFormat("yyyy-mm-dd");
										System.out.println(dated.format(dateValue1));
										String dately = dated.format(dateValue1);
										System.out.println("Dately: " + dately);
										dateValue = dated.parse(dately);
										System.out.println("DateValue: " + dated.parse(dately));
										SimpleDateFormat time = new SimpleDateFormat("kk:mm");
										System.out.println(time.format(dateValue1));
										String timely = time.format(dateValue1);
										timeValue = time.parse(timely);
										System.out.println("TimeValue: " + time.format(timeValue));
										//dateValue = cell.getDateCellValue();
										//System.out.println("DATE: " + dateValue);
									}*/
					        if (dservice.findDonorbyEmail(emailValue) == null) {
					        	donor = new Donor();
					        	donor.setDonorFirstName(nameValue);
					        	donor.setDonorLastName(LNValue);
					        	donor.setDonorEmail(emailValue);
					        	dservice.createDonor(donor);
								System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					        }
					        else {
					        	donor = dservice.findDonorbyEmail(emailValue);
					        	donor.setDonorFirstName(nameValue);
					        	donor.setDonorLastName(LNValue);
					        	donor.setDonorEmail(emailValue);
					        	dservice.updateDonor(donor);
					        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					        }
						}

	                }

	            }
		}
	}
}
