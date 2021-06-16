package com.payMyBuddy.service;

import java.util.Optional;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.BankAccountRepository;


@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	AppUserService appUserservice;

	public Iterable<BankAccount> getBankAccounts() {
		return bankAccountRepository.findAll();
	}

	public Optional<BankAccount> getBankAccountById(Integer id) {
		return bankAccountRepository.findById(id);
	}
	
	public BankAccount getBankAccountByIban(String iban) {
		return bankAccountRepository.findByIban(iban);
		
		
	}
	
	//get balance for a bankAccount
	public Float balance(String iban) {
		BankAccount bankAccount=getBankAccountByIban(iban);	
		Float balance= bankAccount.getBalance();
		return balance;
		
		
	}

	// create the App Account from user email
	public String save(String email, String iban) {
		
		BankAccount account = new BankAccount();
		account.setBalance((float) 0);
		account.setHolder(email);
		account.setIban("FR9999999999999999999999999");
		;
		bankAccountRepository.save(account);
		return "Your account is saved";
	}

	// Save a new providing Bank Account
	public String saveExternalBankAccount(BankAccount bankAccount, String email) {

		BankAccount account = new BankAccount();

		account.setHolder(null);
		account.setIban(null);
		bankAccountRepository.save(account);
		return "Your account is saved";
	}
}