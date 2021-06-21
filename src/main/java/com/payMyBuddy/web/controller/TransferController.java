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

import com.payMyBuddy.dto.AppTransactionDto;
import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.service.AppTransactionService;
import com.payMyBuddy.service.AppUserService;
import com.payMyBuddy.service.BankAccountService;

@Controller
public class TransferController {

	@Autowired
	AppUserService appUserService;
	@Autowired
	BankAccountService bankAccountService;
	@Autowired
	AppUserRepository appUserRepository;
	@Autowired
	AppTransactionDto appTransactionDto;
	@Autowired
	AppTransactionService appTransactionService;

	@GetMapping("/transfer")
	public String transfer(Model model) {
		AppUser appUser = new AppUser();
		model.addAttribute("appTransaction", new AppTransaction());
		model.addAttribute("appTransactions", new ArrayList<>());
		model.addAttribute("appTransaction", appTransactionDto);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();
		AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
		String iban = appUserByUserName.getIban();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);

		model.addAttribute("username", appUserByUserName.getFirstName() + ' ' + appUserByUserName.getLastName());
		model.addAttribute("bankAccount", bankAccount);
		model.addAttribute("balance", bankAccount.getBalance());

		model.addAttribute("appUser", appUser);
		// model.addAttribute("contact", appUserByUserName.getUserContacts());
		AppUser connectedUser = appUserService.findByEmail(userEmail);
		List<AppUser> contact = appUserRepository.findByUserContacts(connectedUser);
		model.addAttribute("userContacts", contact);
		List<AppTransaction> appTransactionList = new ArrayList<>();
		appTransactionList = appTransactionService.ListOfTransactionsId(userEmail);
		model.addAttribute("appTransactions", appTransactionList);
		return "pages/transfer";
	}

	@PostMapping("/transfer")
	public String transferMoney(@ModelAttribute AppTransaction appTransaction, AppUser appUser, Model model)
			throws Exception {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userEmail = authentication.getName();
			AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
			String iban = appUserByUserName.getIban();
			model.addAttribute("ReceiverBankAccountNb", appTransaction.getReceiverBankAccountNb());
			BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
			AppTransaction payment = appTransactionService.savePayment(userEmail,
					appTransaction.getReceiverBankAccountNb(), appTransaction.getAmount(),
					appTransaction.getOperationDescription());
			model.addAttribute("amount", payment.getAmount());
			model.addAttribute("operationDescription", payment.getOperationDescription());
			model.addAttribute("senderBankAccountNb", payment.getSenderBankAccountNb());

			model.addAttribute("username", appUserByUserName.getFirstName() + ' ' + appUserByUserName.getLastName());
			model.addAttribute("bankAccount", bankAccount);
			model.addAttribute("balance", bankAccount.getBalance());

			AppUser connectedUser = appUserService.findByEmail(userEmail);
			List<AppUser> contact = appUserRepository.findByUserContacts(connectedUser);
			model.addAttribute("userContacts", contact);
			List<AppTransaction> appTransactionList = new ArrayList<>();
			appTransactionList = appTransactionService.ListOfTransactionsId(userEmail);
			model.addAttribute("appTransactions", appTransactionList);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "pages/transfer";
		}
		return "redirect:/transfer?successTransfer";
	}

}
