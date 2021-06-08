package com.payMyBuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.repository.AppUserRepository;

@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private BankAccountService bankAccountService;

	private AppUser connectedUser;
	
	public boolean login(String email, String password) {
		return true;
	}
	
	public Iterable<AppUser> getAppUsers() {
		return appUserRepository.findAll();
	}

	public Optional<AppUser> getAppUserById(Integer Id) {
		return appUserRepository.findById(Id);
	}
	public AppUser getAppUserByEmail(String email) {
		return appUserRepository.findByEmail(email);
		
		
	}
	// Get

	// post
	// Check if already in DB
	public AppUser addAppUser(String email) {
		AppUser newAppUser = new AppUser();
		newAppUser.setLastName("DURAND");
		newAppUser.setFirstName("Jacques");
		newAppUser.setEmail("jdurand@gmail.com");
		newAppUser.setPassword("BERTRAND99");
		newAppUser.setIban("FR9999999999999999999999999");
		bankAccountService.save(email);
		return appUserRepository.save(newAppUser);
	}
	// put
	// Check if already in DB

	// Delete?

	// Add a contact in a user contacts list
//		public void addANewContactToMyList(String email) {
//		appUser.getEmail();
//		
	// check if the email exists in DB
	// check if email is not yet in user's list
	// appUser.getUserContacts();
}
