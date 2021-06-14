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
import com.payMyBuddy.model.Role;
import com.payMyBuddy.repository.AppUserRepository;
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
	private HomeController homeController;
//	@Autowired
//	private Role role;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean logged(String email, String password) {
		return true;
	}


	// trouver un user à la connection
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
	// Get

	// post
	// Check if already in DB
	@Transactional
	public AppUser addAppUser(AppUserDto appUserDto) {

		AppUser getUser = userSet(appUserDto);
		AppUser newUser = appUserRepository.save(getUser);
		return newUser;
	}

	public AppUser userSet(AppUserDto appUserDto) {
		AppUser user = new AppUser();

		user.setEmail(appUserDto.getEmailNewUser());
		// user.setPassword(passwordEncoder.encode(appUserDto.getPasswordNewUser()));
		user.setFirstName(appUserDto.getFirstName());
		user.setLastName(appUserDto.getLastName());
		user.setUsername(appUserDto.getUserName());
		user.setIban(appUserDto.getIban());

		//user.setRole(new Role(Integer.valueOf(2), user));

		return user;
	}

//		AppUser newAppUser = new AppUser();
//		newAppUser.setLastName("DURAND");
//		newAppUser.setFirstName("Jacques");
//		newAppUser.setEmail("jdurand@gmail.com");
//		newAppUser.setPassword("BERTRAND99");
//		newAppUser.setIban("FR9999999999999999999999999");
//		bankAccountService.save(email);
//		return appUserRepository.save(newAppUser);
//	}
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

	public AppUser addConnection(String userName, String email) {
		AppUser user = appUserService.findByEmail(userName);
		List<AppUser> listOfContacts = user.getUserContacts();
		if (email.equals(appUserRepository.findByEmail(email).getEmail())) {
			listOfContacts.add(user);
			return user;
		}
		return null;

	}

	// POST
	public ResponseEntity<AppUser> postPerson(AppUser personToPost) {

		return new ResponseEntity<AppUser>(personToPost, HttpStatus.CREATED);
	}

}
