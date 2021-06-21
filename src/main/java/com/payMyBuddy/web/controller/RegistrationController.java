package com.payMyBuddy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.payMyBuddy.dto.AppUserDto;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.service.AppUserService;

@Controller
public class RegistrationController {

	@Autowired
	AppUserService appUserService;
	@Autowired
	AppUserDto appUserDto;

	@GetMapping("/registration")
	public String registration(Model model, BankAccount bankAccount) {

		model.addAttribute("appUser", new AppUser());
		model.addAttribute("appUser", appUserDto);
		model.addAttribute("bankAccount", new BankAccount());
		model.addAttribute("bankAccount", bankAccount);
		return "pages/registration";
	}

	@PostMapping("/registration")
	public String postPerson(@ModelAttribute AppUser appUser, Model model, BankAccount bankAccount) throws Exception {

		try {

			appUserService.addAppUser(appUser, bankAccount);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "pages/registration";
		}
		return "redirect:/registration?successRegistration";
	}

}
