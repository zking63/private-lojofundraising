package com.coding.LojoFundrasing.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Data;
import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.User;
import com.coding.LojoFundrasing.Services.CommitteeService;
import com.coding.LojoFundrasing.Services.DonationService;
import com.coding.LojoFundrasing.Services.DonorService;
import com.coding.LojoFundrasing.Services.EmailService;
import com.coding.LojoFundrasing.Services.ExcelService;
import com.coding.LojoFundrasing.Services.UserService;
import com.coding.LojoFundrasing.Validation.DonorValidation;
import com.coding.LojoFundrasing.Validation.UserValidation;

@Controller
public class LojoController {
	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserValidation uvalidation;
	
	@Autowired
	private DonorValidation dvalidation;
	
	@Autowired
	private DonorService dservice;
	
	@Autowired
	private DonationService donservice;
	
	@Autowired
	private EmailService eservice;
	
	@Autowired 
	private CommitteeService cservice;
	
	@Autowired
	ExcelService excelService;
	
	@RequestMapping("/")
	public String index(@ModelAttribute("user")User user, Model model) {
		model.addAttribute("committees", this.cservice.findAllCommittees());
		return "loginreg.jsp";
	}
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String registerUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		uvalidation.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("committees", this.cservice.findAllCommittees());
			return "loginreg.jsp";
		}
		User newUser = uservice.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		/*Committees committee = cservice.findbyId(id);
		List<Committees> committees = user.getCommittees();
		List<User> users = committee.getUsers();
		committees.add(committee);
		users.add(newUser);
		user.setCommittees(committees);
		committee.setUsers(users);*/
		return "redirect:/home";
	}
	 @RequestMapping(value="/login", method=RequestMethod.POST)
	 public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirs) {
	     // if the user is authenticated, save their user id in session
		 boolean isAuthenticated = uservice.authenticateUser(email, password);
		 if(isAuthenticated) {
			 User u = uservice.findUserbyEmail(email);
			 session.setAttribute("user_id", u.getId());
			 return "redirect:/home";
		 }
	     // else, add error messages and return the login page
		 else {
			 redirs.addFlashAttribute("error", "Invalid Email/Password");
			 return "redirect:/";
		 }
	 }
	 
	 @RequestMapping("/logout")
	 public String logout(HttpSession session) {
	     // invalidate session
		 session.invalidate();
	     // redirect to login page
		 return "redirect:/";
	 }
	@RequestMapping("/committees/new")
	public String newCommittee(Model model, @ModelAttribute("committees")Committees committees) {
		return "newcommittee.jsp";
	}
	@PostMapping("/committees/new")
	public String createCommittee(@ModelAttribute("committees")Committees committees) {
		cservice.createCommittee(committees);
		return "home.jsp";
	}
	 @RequestMapping("/newdonor")
	 public String newDonorPage(@ModelAttribute("donor") Donor donor, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 return "createDonor.jsp";
	 }
	 @PostMapping(value="/newdonor")
	 public String CreateDonor(@Valid @ModelAttribute("donor") Donor donor, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 dvalidation.validate(donor, result);
		 if (result.hasErrors()) {
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 return "createDonor.jsp";
		 }
		 dservice.createDonor(donor);
		 //this.dservice.getDonorData(donor);
		 return "redirect:/donors";
	 }
	 @RequestMapping("/donors")
	 public String donorsPage(@ModelAttribute("donor") Donor donor, Model model, HttpSession session,
			 @Param("startdateD") @DateTimeFormat(iso = ISO.DATE) String startdateD, 
			 @Param("enddateD") @DateTimeFormat(iso = ISO.DATE) String enddateD) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 if (startdateD == null) {
			 startdateD = dateFormat();
		 }
		 if (enddateD == null) {
			 enddateD = dateFormat();
		 }
		 model.addAttribute("startdateD", startdateD);
		 model.addAttribute("enddateD", enddateD);
		 model.addAttribute("dateFormat", dateFormat());
		 //model.addAttribute("donorswithin", this.dservice.DonorsWithinRange(startdateD, enddateD));
		 dservice.DonorsWithinRange(startdateD, enddateD);
		 model.addAttribute("donor", this.dservice.orderMostRecentbyDonorDesc(startdateD, enddateD));
		 return "donors.jsp";
	 }
	private String dateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	private String dateFormat2() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		return df.format(new Date());
	}
	private String dateFormat7daysAgo() {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date sevenDaysAgo = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(sevenDaysAgo);
	}
	private String timeFormat() {
		SimpleDateFormat df = new SimpleDateFormat("kk:mm");
		return df.format(new Date());
	}
	@RequestMapping("/newdonation")
	 public String donationsPage(@ModelAttribute("donation") Donation donation, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("donor", this.dservice.allDonors());
		 model.addAttribute("email", this.eservice.allEmails());
		 model.addAttribute("dateFormat", dateFormat2());
		 model.addAttribute("timeFormat", timeFormat());
		 return "newdonation.jsp";
	 }
	 @PostMapping(value="/newdonation")
	 public String CreateDonation(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (result.hasErrors()) {
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("donor", this.dservice.allDonors());
			 model.addAttribute("email", this.eservice.allEmails());
			 model.addAttribute("dateFormat", dateFormat2());
			 return "newdonation.jsp";
		 }
		 Emails email = donation.getEmailDonation();
		 Donor donor = dservice.findDonorbyEmail(donation.getDonor().getDonorEmail());
		 donservice.createDonation(donation);
		 this.eservice.getEmailData(email);
		 this.dservice.getDonorData(donor);
		 return "redirect:/home";
	 }
	 @RequestMapping("/newemail")
	 public String newEmailpage(@ModelAttribute("email") Emails email, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("dateFormat",  dateFormat2());
		 model.addAttribute("timeFormat", timeFormat());
		 return "newemail.jsp";
	 }
	 @PostMapping(value="/newemail")
	 public String CreateEmail(@Valid @ModelAttribute("email") Emails email, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (result.hasErrors()) {
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 return "newemail.jsp";
		 }
		 model.addAttribute("dateFormat", dateFormat2());
		 model.addAttribute("timeFormat", timeFormat());
		 eservice.createEmail(email);
		 this.eservice.getEmailData(email);
		 return "redirect:/emails";
	 }
	 @RequestMapping("/emails")
	 public String Emailpage(@ModelAttribute("email") Emails email, Model model, HttpSession session,
			 @Param("startdateE") @DateTimeFormat(iso = ISO.DATE) String startdateE, 
			 @Param("enddateE") @DateTimeFormat(iso = ISO.DATE) String enddateE) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 if (startdateE == null) {
			 startdateE = dateFormat7daysAgo();
		 }
		 if (enddateE == null) {
			 enddateE = dateFormat();
		 }
		 model.addAttribute("startdateE", startdateE);
		 model.addAttribute("enddateE", enddateE);
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("email", this.eservice.EmailTest(startdateE, enddateE));
		 //model.addAttribute("average", this.eservice.getAv(email.getId()));
		 return "emails.jsp";
	 }
	 @RequestMapping("/donors/{id}")
	 public String showDonor(@PathVariable("id") long id, Model model, HttpSession session, @ModelAttribute("donor")Donor donor) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("donor", this.dservice.findbyId(id));
		 return "/donors/showdonor.jsp";
	 }
	@RequestMapping("/donors/delete/{id}")
	public String DeleteDonor(@PathVariable("id") Long id, HttpSession session, Model model) {
		this.dservice.delete(id);
		return "redirect:/donors";
	}
	@RequestMapping(value="/donors/edit/{id}")
	public String EditDonor(@PathVariable("id") long id, HttpSession session, Model model) {
		Long user_id = (Long)session.getAttribute("user_id");
		if (user_id == null) {
			return "redirect:/";
		}
		User user = uservice.findUserbyId(user_id);
		model.addAttribute("donor", dservice.findbyId(id));
		model.addAttribute("user", user);
		return "/donors/editdonor.jsp";
	}
	 @RequestMapping(value="/donors/edit/{id}", method=RequestMethod.POST)
	 public String UpdateDonor(@Valid @ModelAttribute("donor") Donor donor, BindingResult result, Model model, HttpSession session) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (result.hasErrors()) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 this.dservice.updateDonor(donor);
		 return "redirect:/donors";
	 }
	 @RequestMapping("/emails/{id}")
	 public String showEmail(@PathVariable("id") long id, Model model, HttpSession session, @ModelAttribute("email")Emails email) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 model.addAttribute("emails", this.eservice.findEmailbyId(id));
		 return "/emails/showemail.jsp";
	 }
	 @RequestMapping("/home")
	 public String homePage(Model model, HttpSession session, @ModelAttribute("donations")Donation donation,
			 @Param("startdate") @DateTimeFormat(iso = ISO.DATE) String startdate, 
			 @Param("enddate") @DateTimeFormat(iso = ISO.DATE) String enddate) {
		 Long user_id = (Long)session.getAttribute("user_id");
		 if (user_id == null) {
			 return "redirect:/";
		 }
		 User user = uservice.findUserbyId(user_id);
		 model.addAttribute("user", user);
		 if (startdate == null) {
			 startdate = dateFormat();
		 }
		 if (enddate == null) {
			 enddate = dateFormat();
		 }
		 model.addAttribute("dateFormat", dateFormat());
		 model.addAttribute("user", uservice.findUserbyId(user_id));
		 model.addAttribute("startdate", startdate);
		 model.addAttribute("enddate", enddate);
		 model.addAttribute("donations", donservice.DonTest(startdate, enddate));
		 return "home.jsp";
	 }
		@RequestMapping("/emails/delete/{id}")
		public String DeleteEmail(@PathVariable("id") Long id, HttpSession session, Model model) {
			this.eservice.delete(id);
			return "redirect:/emails";
		}
		@RequestMapping(value="/emails/edit/{id}")
		public String EditEmail(@PathVariable("id") long id, HttpSession session, Model model) {
			Long user_id = (Long)session.getAttribute("user_id");
			if (user_id == null) {
				return "redirect:/";
			}
			User user = uservice.findUserbyId(user_id);
			model.addAttribute("dateFormat", dateFormat2());
			model.addAttribute("timeFormat", timeFormat());
			model.addAttribute("email", eservice.findEmailbyId(id));
			model.addAttribute("user", user);
			return "/emails/editemail.jsp";
		}
		 @RequestMapping(value="/emails/edit/{id}", method=RequestMethod.POST)
		 public String UpdateEmail(@Valid @ModelAttribute("email") Emails email, BindingResult result, Model model, HttpSession session) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (result.hasErrors()) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("dateFormat", dateFormat2());
			 model.addAttribute("timeFormat", timeFormat());
			 model.addAttribute("user", user);
			 List<Donation> donations = email.getEmaildonations();
			 eservice.updateEmail(email);
			 //this.eservice.getEmailData(email);
			 /*for (int i = 0; i < donations.size(); i++) {
				 Donor donor = donations.get(i).getDonor();
				 this.dservice.getDonorData(donor);
			 }*/
			 return "redirect:/emails";
		 }
		 //edit and delete donations homepage
			@RequestMapping("/donations/delete/{id}")
			public String DeleteDonation(@PathVariable("id") Long id, HttpSession session, Model model) {
				Donation donation = this.donservice.findDonationbyId(id);
				this.donservice.delete(id);
				Emails email = donation.getEmailDonation();
				Donor donor = donation.getDonor();
				this.eservice.getEmailData(email);
				this.dservice.getDonorData(donor);
				return "redirect:/home";
			}
			@RequestMapping(value="/donations/edit/{id}")
			public String EditDonation(@PathVariable("id") long id, HttpSession session, Model model) {
				Long user_id = (Long)session.getAttribute("user_id");
				if (user_id == null) {
					return "redirect:/";
				}
				User user = uservice.findUserbyId(user_id);
				Donation donation = this.donservice.findDonationbyId(id);
				model.addAttribute("donation", donation);
				model.addAttribute("user", user);
				model.addAttribute("donor", this.dservice.allDonors());
				model.addAttribute("email", this.eservice.allEmails());
				model.addAttribute("dateFormat", dateFormat2());
				model.addAttribute("timeFormat", timeFormat());
				return "/donations/editdonation.jsp";
			}
		 @RequestMapping(value="/donations/edit/{id}", method=RequestMethod.POST)
		 public String UpdateDonation(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, Model model, HttpSession session) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (result.hasErrors()) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("donor", this.dservice.allDonors());
			 model.addAttribute("email", this.eservice.allEmails());
			 model.addAttribute("dateFormat", dateFormat2());
			 model.addAttribute("timeFormat", timeFormat());
			 Emails email = donation.getEmailDonation();
			 Donor donor = donation.getDonor();
			 donservice.createDonation(donation);
			 this.eservice.getEmailData(email);
			 this.dservice.getDonorData(donor);
			 return "redirect:/home";
		 }
		 //edit and delete donations donor page
			@RequestMapping("/donations/delete/{id}/donor")
			public String DeleteDonationfromDonorPage(@PathVariable("id") Long id, HttpSession session, Model model) {
				Donor donor = this.donservice.findDonationbyId(id).getDonor();
				long donorid = donor.getId();
				this.donservice.delete(id);
				return "redirect:/donors/" + donorid;
			}
		 //sorting homepage
		 @RequestMapping(value="/home/sortdown")
		 public String sortdownPost(Model model, HttpSession session, @ModelAttribute("donations")Donation donation,
				 @RequestParam("startdate") @DateTimeFormat(iso = ISO.DATE) String startdate, 
				 @RequestParam("enddate") @DateTimeFormat(iso = ISO.DATE) String enddate, @RequestParam("field") String field) throws ParseException {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdate", startdate);
			 model.addAttribute("enddate", enddate);
			 model.addAttribute("field",field);
			 List<Donation> donations = null;
			 enddate = enddate + " 23:59:00";
			 if (field.equals("amount")) {
				 donations = this.donservice.orderAmounts2(startdate, enddate);
			 }
			 if (field.equals("datetime")) {
				 donations = this.donservice.DonTest(startdate, enddate);
			 }
			 model.addAttribute("donations", donations);
			 return "home.jsp";
		 }
		 @RequestMapping(value="/home/sortup")
		 public String sortUpPost(Model model, HttpSession session, @ModelAttribute("donations")Donation donation,
				 @RequestParam("startdate") @DateTimeFormat(iso = ISO.DATE) String startdate, 
				 @RequestParam("enddate") @DateTimeFormat(iso = ISO.DATE) String enddate, @RequestParam("field") String field) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdate", startdate);
			 model.addAttribute("enddate", enddate);
			 model.addAttribute("field",field);
			 List<Donation> donations = null;
			 enddate = enddate + " 23:59:00";
			 if (field.equals("amount")) {
				 donations = this.donservice.orderAmounts(startdate, enddate);
			 }
			 if (field.equals("datetime")) {
				 donations = this.donservice.DonTestAsc(startdate, enddate);
			 }
			 model.addAttribute("donations", donations);
			 return "home.jsp";
		 }
		 //sorting emails page
		 @RequestMapping(value="/test")
		 public String test(Model model) {
			 List <Data> data = this.eservice.getEmailDatatest();
			 model.addAttribute("data", data);
			 return "test.jsp";
		 }
		 @RequestMapping(value="/emails/sortdown")
		 public String sortdownEmail(Model model, HttpSession session,
				 @RequestParam("startdateE") @DateTimeFormat(iso = ISO.DATE) String startdateE, 
				 @RequestParam("enddateE") @DateTimeFormat(iso = ISO.DATE) String enddateE, @RequestParam("field") String field) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdateE", startdateE);
			 model.addAttribute("enddateE", enddateE);
			 model.addAttribute("field",field);
			 List<Emails> email = null;
			 if (field.equals("datetime")) {
				 email = eservice.EmailTest(startdateE, enddateE);
			 }
			 if (field.equals("average")) {
				 email = eservice.AvDesc(startdateE, enddateE);
			 }
			 if (field.equals("sum")) {
				 email = eservice.SumDesc(startdateE, enddateE);
			 }
			 if (field.equals("donationscount")) {
				 email = eservice.DonationsCountDesc(startdateE, enddateE);
			 }
			 if (field.equals("donorcount")) {
				 email = eservice.DonorCountDesc(startdateE, enddateE);
			 }
			 model.addAttribute("email", email);
			 return "emails.jsp";
		 }
		 @RequestMapping(value="/emails/sortup")
		 public String sortUpEmail(Model model, HttpSession session,
				 @Param("startdateE") @DateTimeFormat(iso = ISO.DATE) String startdateE, 
				 @Param("enddateE") @DateTimeFormat(iso = ISO.DATE) String enddateE, @Param("field") String field) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdateE", startdateE);
			 model.addAttribute("enddateE", enddateE);
			 model.addAttribute("field",field);
			 List<Emails> email = null;
			 if (field.equals("datetime")) {
				 email = this.eservice.EmailTestAsc(startdateE, enddateE);
			 }
			 if (field.equals("average")) {
				 email = this.eservice.AverageAsc(startdateE, enddateE);
			 }
			 if (field.equals("sum")) {
				 email = eservice.SumAsc(startdateE, enddateE);
			 }
			 if (field.equals("donationscount")) {
				 email = eservice.DonationsCountAsc(startdateE, enddateE);
			 }
			 if (field.equals("donorcount")) {
				 email = eservice.DonorCountAsc(startdateE, enddateE);
			 }
			 model.addAttribute("email", email);
			 return "emails.jsp";
		 }
		 @RequestMapping(value="/donors/sortdown")
		 public String sortdownDonors(Model model, HttpSession session,
				 @RequestParam("startdateD") @DateTimeFormat(iso = ISO.DATE) String startdateD, 
				 @RequestParam("enddateD") @DateTimeFormat(iso = ISO.DATE) String enddateD, @RequestParam("field") String field) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdateD", startdateD);
			 model.addAttribute("enddateD", enddateD);
			 model.addAttribute("field", field);
			 List<Donor> donors = null;
			 if (field.equals("latestdonation")) {
				 donors = this.dservice.orderMostRecentbyDonorDesc(startdateD, enddateD);
			 }
			 if (field.equals("donationcount")) {
				 donors = this.dservice.orderDonorCountDesc(startdateD, enddateD);
			 }
			 if (field.equals("donoraverage")) {
				 donors = this.dservice.orderAverageDesc(startdateD, enddateD);
			 }
			 if (field.equals("donorsum")) {
				 donors = this.dservice.orderDonorsumDesc(startdateD, enddateD);
			 }
			 if (field.equals("mostrecentamount")) {
				 donors = this.dservice.orderMostrecentAmountDesc(startdateD, enddateD);
			 }
			 dservice.DonorsWithinRange(startdateD, enddateD);
			 model.addAttribute("donor", donors);
			 return "donors.jsp";
		 }
		 @RequestMapping(value="/donors/sortup")
		 public String sortUpDonors(Model model, HttpSession session,
				 @RequestParam("startdateD") @DateTimeFormat(iso = ISO.DATE) String startdateD, 
				 @RequestParam("enddateD") @DateTimeFormat(iso = ISO.DATE) String enddateD, @RequestParam("field") String field) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			 User user = uservice.findUserbyId(user_id);
			 model.addAttribute("user", user);
			 model.addAttribute("dateFormat", dateFormat());
			 model.addAttribute("startdateD", startdateD);
			 model.addAttribute("enddateD", enddateD);
			 model.addAttribute("field", field);
			 List<Donor> donors = null;
			 if (field.equals("latestdonation")) {
				 donors = this.dservice.orderMostRecentbyDonorAsc(startdateD, enddateD);
			 }
			 if (field.equals("donationcount")) {
				 donors = this.dservice.orderDonorCountAsc(startdateD, enddateD);
			 }
			 if (field.equals("donoraverage")) {
				 donors = this.dservice.orderAverageAsc(startdateD, enddateD);
			 }
			 if (field.equals("donorsum")) {
				 donors = this.dservice.orderDonorsumAsc(startdateD, enddateD);
			 }
			 if (field.equals("mostrecentamount")) {
				 donors = this.dservice.orderMostrecentAmountAsc(startdateD, enddateD);
			 }
			 dservice.DonorsWithinRange(startdateD, enddateD);
			 model.addAttribute("donor", donors);
			 return "donors.jsp";
		 }
		 //imports
		@RequestMapping(value="/import/donations")
		public String importdonations(HttpSession session) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			return "import.jsp";
		}
		@PostMapping("/import/donations")
		public String readExcel(HttpSession session, MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			excelService.readData(user_id, file);
			return "redirect:/home";
		}
		@RequestMapping(value="/import/emails")
		public String importEmails(HttpSession session) {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			return "importemails.jsp";
		}
		@PostMapping("/import/emails")
		public String readExcelemails(HttpSession session, MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {
			 Long user_id = (Long)session.getAttribute("user_id");
			 if (user_id == null) {
				 return "redirect:/";
			 }
			excelService.readEmailData(user_id, file);
			return "redirect:/home";
		}
		@RequestMapping("/tester")
		public String tester(Model model) {
			model.addAttribute("committees", cservice.findAllCommittees());
			return "test.jsp";
		}
}