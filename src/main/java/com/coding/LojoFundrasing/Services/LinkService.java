package com.coding.LojoFundrasing.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.LojoFundrasing.Models.Link;
import com.coding.LojoFundrasing.Repos.LinkRepo;

@Service
public class LinkService {
	
	@Autowired
	private LinkRepo lrepo;
	
	public Link createLink(Link link) {
		return lrepo.save(link);
	}
}
