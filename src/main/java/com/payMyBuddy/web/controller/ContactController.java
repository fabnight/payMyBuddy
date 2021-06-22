package com.payMyBuddy.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.payMyBuddy.dto.AppUserDto;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.service.AppUserService;

@Controller
public class ContactController {

	@Autowired
	AppUserService appUserService;
	@Autowired
	AppUserDto appUserDto;

	@GetMapping("/contact")
	public String contact(AppUser appUser, Model model) {
		model.addAttribute("appUser", new AppUser());
		model.addAttribute("email", appUser.getUserContacts());
		model.addAttribute("lastName", appUserDto);
		model.addAttribute("contact", appUser.getUserContacts());
		return "pages/contact";
	}

	@PostMapping("/contact")
	public String addContact(@ModelAttribute AppUser appUser, Model model) {

		try {

			List<AppUser> contact = appUserService.addConnection(appUser.getEmail());
			model.addAttribute("contact", contact);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "pages/contact";
		}
		return "redirect:/contact?success";
	}
}