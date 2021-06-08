package com.payMyBuddy.service;

import java.util.Optional;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.BankAccountRepository;

@DynamicUpdate
@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public Iterable<BankAccount> getBankAccounts() {
		return bankAccountRepository.findAll();
	}

	public Optional<BankAccount> getBankAccountById(Integer id) {
		return bankAccountRepository.findById(id);
	}

	// create the App Account from user email
	public String save(String email) {

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