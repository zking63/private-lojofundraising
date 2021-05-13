package com.coding.LojoFundrasing.Controllers;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.coding.LojoFundrasing.Services.ExcelService;

@Controller
public class ExcelController {
	@Autowired
	private ExcelService excelService;
	
	@RequestMapping(value="/")
	public String home() {
		return "home.jsp";
	}
	@RequestMapping(value="/import")
	public String home2() {
		return "import.jsp";
	}
	@PostMapping("/import")
	public String readExcel(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {
		excelService.readData(file);
		return "home.jsp";
	}
}
