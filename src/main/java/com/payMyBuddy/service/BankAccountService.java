package com.payMyBuddy.service;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	//create the App Account from user email
	public String save(String email) {
	   
	    BankAccount account = new BankAccount();
	    account.setBalance(null);
	    account.setHolder(email);
	    account.setBankAccountNb("PMB1111111111");
	    bankAccountRepository.save(account);
	    return "Your account is saved";
	  }
	
	// Save a new providing Bank Account
	public String save(BankAccount bankAccount, String email) {
		   
	    BankAccount account = new BankAccount();
	   
	    account.setHolder(null);
	    account.setBankAccountNb(null);
	    bankAccountRepository.save(account);
	    return "Your account is saved";
	  }
}