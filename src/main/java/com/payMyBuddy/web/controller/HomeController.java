package com.payMyBuddy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.service.BankAccountService;

@Controller
public class HomeController {

	@Autowired
	BankAccountService bankAccountService;
	@Autowired
	AppUserRepository appUserRepository;

	@RequestMapping("/home")
	public String home(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, Model model) {
		model.addAttribute("username", username);
		model.addAttribute("password", password);

		return "pages/home";

	}

	@PostMapping("/home")
	public String usernameSubmit(@ModelAttribute Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();

		AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
		String iban = appUserByUserName.getIban();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
		model.addAttribute("bankAccount", bankAccount);
		model.addAttribute("balance", bankAccount.getBalance());
		return "pages/menu";
	}

}
