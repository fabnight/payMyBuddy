package com.payMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.BankAccountRepository;

@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public Iterable<BankAccount> getBankAccounts() {
		return bankAccountRepository.findAll();

	}
}