package com.payMyBuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.transaction.annotation.Transactional;

import com.payMyBuddy.dto.AppUserDto;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.model.Role;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;
import com.payMyBuddy.web.controller.HomeController;


@Service
public class AppUserService {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private HomeController homeController;
//	@Autowired
//	private Role role;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean logged(String email, String password) {
		return true;
	}


	// get a user after authentication
	@Transactional(rollbackFor = Exception.class)
	public AppUser findByEmail(String username) {
		AppUser	userConnected;
		try {
			userConnected = appUserRepository.findByEmail(username);
		} catch (Exception e) {
			throw e;
		}
		return userConnected;
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
	
	// Check if already in DB
	@Transactional
	public AppUser addAppUser(AppUser appUser, BankAccount bankAccount) {
		boolean existingEmail =appUserRepository.existsAppUserByEmail(appUser.getEmail());
		if(existingEmail=true) {System.out.println("Can not register you, this email is already existing");}
		else {
		AppUser getUser = userSet(appUser, bankAccount);
		AppUser newUser = appUserRepository.save(getUser);
		BankAccount getBankAccount = bankAccountSet(appUser, bankAccount);
		bankAccountRepository.save(getBankAccount);
		return newUser;}
		return null;
	}

	public AppUser userSet(AppUser appUser, BankAccount bankAccount) {
		AppUser user = new AppUser();
		Role role = new Role();
		role.setId(2);
		role.setRoleName("User");
		user.setEmail(appUser.getEmail());
		user.setPassword(passwordEncoder.encode(appUser.getPassword()));
		user.setFirstName(appUser.getFirstName());
		user.setLastName(appUser.getLastName());
		user.setUsername(appUser.getEmail());
		user.setIban(appUser.getIban());
		user.setRole(role);
		
		return user;
	}
	public BankAccount bankAccountSet(AppUser appUser, BankAccount bankAccount) {
		BankAccount bankAccounttoAssociate = new BankAccount();
		bankAccounttoAssociate.setIban(appUser.getIban());
		bankAccounttoAssociate.setBalance(0.00F);
		bankAccounttoAssociate.setHolder(bankAccount.getHolder());
		
		return bankAccounttoAssociate;
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

	@ModelAttribute("firstName")
	public List<AppUser> firstName() {
		return appUserRepository.findAll();
	}

//Ajouter une connection(nouveau contact)
	
	public List<AppUser> addConnection(String userName, String email) {
		AppUser user = appUserService.findByEmail(userName);
		List<AppUser> listOfContacts = user.getUserContacts();
		//check if this connection is not already in the user list
		boolean existingEmail =appUserRepository.existsListOfcontactsByEmail(email);
		if(existingEmail=true) {System.out.println("This connection"+" "+email+" is already in your list of contacts");}
		else {
		
		AppUser newContact = appUserService.getAppUserByEmail(email);
		
		//newUser.setUserId(1);
		listOfContacts.add(newContact);
		appUserRepository.save(listOfContacts);
		return listOfContacts;}
		return null;
	}

//	public List<AppUser> addConnection(String userName, AppUser appUser) {
//		AppUser user = appUserService.findByEmail(userName);
//		List<AppUser> listOfContacts = user.getUserContacts();
//		String newContact = appUser.getEmail();
//		AppUser newUser = appUserService.findByEmail(newContact);
//		//newUser.setUserId(1);
//		listOfContacts.add(newUser);
//		appUserRepository.save(listOfContacts);
//		return listOfContacts;
//	}


	

	// POST
	public ResponseEntity<AppUser> postPerson(AppUser personToPost) {

		return new ResponseEntity<AppUser>(personToPost, HttpStatus.CREATED);
	}

}
