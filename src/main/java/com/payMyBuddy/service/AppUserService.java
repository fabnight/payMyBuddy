package com.payMyBuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.repository.AppUserRepository;

@Service
public class AppUserService {
	@Autowired
	private AppUserRepository appUserRepository;
//	@Autowired
//	private AppUser appUser;
	
	public Iterable<AppUser> getUsers() {
		return appUserRepository.findAll();

	}

	//Get
	 
	//post
	//Check if already in DB
	
	//put
	//Check if already in DB
	
	//Delete?
	
	//Add a contact in a user contacts list
		public void addANewContactToMyList(String email) {
		appUser.getEmail();
		
		//check if the email exists in DB
		//check if email is not yet in user's list
		appUser.getUserContacts();
	}
	
}
