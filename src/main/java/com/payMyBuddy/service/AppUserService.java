package com.payMyBuddy.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.transaction.annotation.Transactional;
import com.payMyBuddy.model.AppUser;
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

	private AppUser connectedUser;
	
	public boolean logged(String email, String password) {
		return true;
	}
	
//@PostMapping	
//private AppUser connect() {
//
//@ModelAttribute("email, password")
//public boolean checkAuthentification() {
//	String email;
//	String password;
//	Model model;
//    homeController.home(email, password, model);	
//    	System.out.println("hello");
//    }
//}
//
//  //Verify if user already exist or account inactive
//	AppUser appUserBypassword = appUserRepository.findByEmailAndPassword("fgarnier@hotmail.com", "azertyuiop");
//  AppUser user = appUserService.findOne(appUserRepository....getEmail());
//
//  if (userBuddyServiceI.existsUserBuddyByEmail(userRegistrationDto.getEmail())) {
//    Boolean active = user.isActive();
//    if(!active) {
//      return "redirect:/suscribe?inactive";
//    }
//    return "redirect:/suscribe?error";
//  }
//   else {
//    userBuddyServiceI.save(userRegistrationDto);
//    return "redirect:/suscribe?successRegistration";
//  }
//}

	
	
	@Transactional
	public AppUser findByUsernameOrEmail(String usernameOrEmail) {
		AppUser user = null;
		try {
		user= appUserRepository.findByUsernameOrEmail(usernameOrEmail);
	} catch (Exception e) {
		throw e;
	}
	return user;	
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
