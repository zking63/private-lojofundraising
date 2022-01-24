package com.coding.LojoFundrasing.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Committees;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Models.Link;
import com.coding.LojoFundrasing.Repos.LinkRepo;

@Service
public class LinkService {
	
	@Autowired
	private LinkRepo lrepo;
	
	@Autowired
	private CommitteeService cservice;
	
	public Link createLink(Link link) {
		return lrepo.save(link);
	}
	
	public Link updateLink(Link link) {
		return lrepo.save(link);
	}
	
	public Link findAndSetUpLinkfromUpload(String emaillink, Committees committee) {
		Link link = null;
		Date date = new Date();
		Boolean committeeSetList = false;
		if (emaillink == null) {
			return link;
		}
		else {
			System.out.println("emaillink: " + emaillink);
			int iend = emaillink.indexOf("?"); 

			if (iend != -1) 
			{
			    emaillink = emaillink.substring(0 , iend); //this will give abc
			}
			System.out.println("emaillink after substring: " + emaillink);
			link = lrepo.findLinkbyNameandCommittee(emaillink, committee.getId());
			if (link == null) {
				link = new Link();
				link.setCreatedAt(date);
				link.setLinkname(emaillink);
				link.setCommittee(committee);
	        	while (committeeSetList == false) {
	    			if (committee.getLinks() == null || committee.getLinks().size() == 0) {
	    				List<Link> links = new ArrayList<Link>();
	    				links.add(link);
	    				committee.setLinks(links);
	    				cservice.createCommittee(committee);
	    				committeeSetList = true;
	    			}
	    			else {
	    				List<Link> links = committee.getLinks();
	    				links.add(link);
	    				committee.setLinks(links);
	    				cservice.createCommittee(committee);
	    				committeeSetList = true;
	    			}
	        	}
			}
			updateLink(link);
			return link;
		}
	}
	public void CalculateLinkData (Link link, Committees committee) {
		if (link == null) {
			return;
		}
		Long donations;
	    Long donors;
	    
	    Long emailsUsingLink;
	    
	    Long clicksFromEmail;
	}
}
