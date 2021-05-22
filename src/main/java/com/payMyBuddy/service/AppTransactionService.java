package com.payMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.repository.AppTransactionRepository;

@Service
public class AppTransactionService {
	@Autowired
	private AppTransactionRepository appTransactionRepository;

	public Iterable<AppTransaction> getAppTransactions() {
		return appTransactionRepository.findAll();

	}
}