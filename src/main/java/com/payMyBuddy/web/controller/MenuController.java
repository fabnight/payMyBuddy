package com.payMyBuddy.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;
import com.payMyBuddy.service.AppTransactionService;
import com.payMyBuddy.service.BankAccountService;

@Controller
public class MenuController {
	@Autowired
	BankAccountService bankAccountService;
	@Autowired
	BankAccountRepository bankAccountRepository;
	@Autowired
	AppUserRepository appUserRepository;
	@Autowired
	AppTransactionService appTransactionService;

	@GetMapping("/menu")
	public String menu(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();

		AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
		String iban = appUserByUserName.getIban();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);

		model.addAttribute("username", appUserByUserName.getFirstName() + ' ' + appUserByUserName.getLastName());
		model.addAttribute("bankAccount", bankAccount);
		model.addAttribute("balance", bankAccount.getBalance());
		model.addAttribute("appTransaction", new AppTransaction());
		List<AppTransaction> a = new ArrayList<>();
		model.addAttribute("transactions", a);
		model.addAttribute("contact", new AppUser());

		return "pages/menu";
	}

	@PostMapping("/menu")
	public String postTransaction(@ModelAttribute AppTransaction appTransaction, BankAccount bankAccount, Model model,
			@RequestParam(value = "action", required = false) String action) throws Exception {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userEmail = authentication.getName();
			AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
			model.addAttribute("username", appUserByUserName.getFirstName() + ' ' + appUserByUserName.getLastName());
			String iban = appUserByUserName.getIban();
			// BankAccount bankAccountToUpdate =
			// bankAccountService.getBankAccountByIban(iban);
			model.addAttribute("iban", bankAccount.getIban());
			String holder = bankAccount.getHolder();
			model.addAttribute("holder", holder);
			if (action.equals("iban")) {
				bankAccountService.saveNewBankAccount(appUserByUserName, bankAccount, iban, holder);
			}
			if (action.equals("withdraw")) {
				appTransactionService.savewithdraw(userEmail, appTransaction.getAmount());
				//Float balance = bankAccount.getBalance();
				
			}
			if (action.equals("fund")) {
				appTransactionService.fundAppAccount(userEmail, appTransaction.getAmount());

			}
			model.addAttribute("balance", bankAccountService.balance(iban));
			List<AppTransaction> transactions = appTransactionService.ListOfTransactions(userEmail);
			model.addAttribute("transactions", transactions);

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "pages/menu";
		}
		return "redirect:/menu?successPayment";
	}
}
