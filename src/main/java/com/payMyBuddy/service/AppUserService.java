package com.payMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.repository.AppUserRepository;

@Service
public class AppUserService {
	@Autowired
	private AppUserRepository appUserRepository;
	
	public Iterable<AppUser> getAppUsers(){
		return appUserRepository.findAll();
	}

	//Get
	 
	//post
	//Check if already in DB
	public AppUser addAppUser(AppUser appUser) {
		AppUser newAppUser = new AppUser();
		newAppUser.setLastName("BERTRAND");
		newAppUser.setFirstName("Jacques");
		newAppUser.setEmail("jbertrand@gmail.com");
		newAppUser.setPassword("BERTRAND99");
		newAppUser.setIban("FR9999999999999999999999999");
		return appUserRepository.save(newAppUser);		
	}
	//put
	//Check if already in DB
	
	//Delete?
	
	//Add a contact in a user contacts list
//		public void addANewContactToMyList(String email) {
//		appUser.getEmail();
//		
		//check if the email exists in DB
		//check if email is not yet in user's list
		//appUser.getUserContacts();
	}
	

