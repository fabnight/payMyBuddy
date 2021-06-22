package com.payMyBuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	AppUserService appUserservice;
	@Autowired
	AppUserRepository appUserRepository;

	public Iterable<BankAccount> getBankAccounts() {
		return bankAccountRepository.findAll();
	}

	public Optional<BankAccount> getBankAccountById(Integer id) {
		return bankAccountRepository.findById(id);
	}

	public BankAccount getBankAccountByIban(String iban) {
		return bankAccountRepository.findByIban(iban);

	}

	// get balance for a bankAccount
	public Float balance(String iban) {
		BankAccount bankAccount = getBankAccountByIban(iban);
		Float balance = bankAccount.getBalance();
		return balance;

	}

	// change for a new Iban
	public String saveNewBankAccount(AppUser appUser, BankAccount bankAccount, String iban, String holder) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = authentication.getName();

		AppUser appUserByUserName = appUserRepository.findByEmail(userEmail);
		BankAccount AccountToUpdate = bankAccountRepository.findById(appUserByUserName.getUserId());

		appUserByUserName.setIban(bankAccount.getIban());
		AccountToUpdate.setIban(bankAccount.getIban());
		AccountToUpdate.setHolder(holder);
		bankAccountRepository.save(AccountToUpdate);

		return "Your account is saved";
	}
}