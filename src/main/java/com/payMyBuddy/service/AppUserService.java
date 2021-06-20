package com.payMyBuddy.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private static final Logger logger = LogManager.getLogger("AppUserService");
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
		AppUser userConnected;
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

	// Method to register a new user

	@Transactional
	public AppUser addAppUser(AppUser appUser, BankAccount bankAccount) throws Exception {

		boolean alreadyExistingUser = appUserRepository.existsAppUserByEmail(appUser.getEmail());
// Check if already in DB
		if (alreadyExistingUser) {
			throw new Exception("Can not register you, this email is already existing for a user");

		} else {
			AppUser getUser = userSet(appUser, bankAccount);
			AppUser newUser = appUserRepository.save(getUser);
			BankAccount getBankAccount = bankAccountSet(appUser, bankAccount);
			bankAccountRepository.save(getBankAccount);
			return newUser;
		}
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

	// Method to create a new bank account associated to a user
	public BankAccount bankAccountSet(AppUser appUser, BankAccount bankAccount) {
		BankAccount bankAccounttoAssociate = new BankAccount();
		bankAccounttoAssociate.setIban(appUser.getIban());
		bankAccounttoAssociate.setBalance(0.00F);
		bankAccounttoAssociate.setHolder(bankAccount.getHolder());

		return bankAccounttoAssociate;
	}

//Method to add a new connection in a user's contacts list

	public List<AppUser> addConnection(String newContactEmail) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		// check if new contact mail exists as AppUser
		boolean validContact = appUserRepository.existsAppUserByEmail(newContactEmail);
		if (validContact) {
//check if the new contact is not already in the connected user's contacts list
			AppUser connectedUser = appUserService.findByEmail(userEmail);
			List<AppUser> listOfContacts = connectedUser.getUserContacts();
			boolean contactAlreadyExisting = listOfContacts.stream()
					.filter(user -> user.getEmail().equals(newContactEmail)).findAny().isPresent();

			if (contactAlreadyExisting) {
				throw new Exception("this email already belongs to your contacts.");
			} else {
				AppUser newContact = appUserService.getAppUserByEmail(newContactEmail);
				listOfContacts.add(newContact);
				appUserRepository.save(connectedUser);
				logger.info("This new connection is now stored in your contact list.");
			}
			return listOfContacts;
		} else {
			throw new Exception("Please check this email : Unknown user");
		}
	}
}
