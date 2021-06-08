package com.payMyBuddy.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.dto.BankAccountDto;
import com.payMyBuddy.dto.TransactionDto;
import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppTransactionRepository;
import com.payMyBuddy.repository.BankAccountRepository;
import com.payMyBuddy.constants.TransactionConstants;

@Service
public class AppTransactionService {
	
	@Autowired
	private AppTransactionRepository appTransactionRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private BankAccountDto bankAccountDto;
	
	
	public Iterable<AppTransaction> getAppTransactions() {
		return appTransactionRepository.findAll();
	}
	public Optional<AppTransaction> getAppTransactionById(Integer id) {
		return appTransactionRepository.findById(id);

	}

	// method to transfer money to 3rd party
	public AppTransaction savePayment(String bankAccountNumber) {
		
		AppTransaction appTransaction = new AppTransaction();
		
		BankAccount bankAccount = new BankAccount();
		Float transactionAmount = 90f;
	
		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
			bankAccount.setBalance(800 - transactionAmount - transactionFees);//bankAccount.getBalance()
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("payment");
			appTransaction.setOperationDescription("payment");
			appTransaction.setReceiverBankAccountNb("FR7612341234123412341234124");
			appTransaction.setSenderBankAccountNb("FR7612341234123412341234123");

			return appTransactionRepository.save(appTransaction);
				
			
		}
//		return "your account is not sufficiently funded";
//	}

	// method to provide money from BankAccount to AppAccount
	public AppTransaction fundAppAccount(String bankAccountNumber) {
		
		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = new BankAccount();
		float balance =bankAccountDto.getBalance(bankAccountNumber);
		Float transactionAmount = (float) 600.00;
//////////
		if (transactionAmount != null) {
			bankAccount.setBalance(100+ transactionAmount);//bankAccount.getBalance()
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees((float) 0.00);
			appTransaction.setOperationDate(TransactionConstants.transactiondate);
			appTransaction.setOperationType("fund");
			appTransaction.setOperationDescription("fund");
			appTransaction.setReceiverBankAccountNb("FR7612341234123412341234124");
			appTransaction.setSenderBankAccountNb("FR9999999999999999999999999");

			Optional<BankAccount> accountToUpdate = bankAccountRepository.findById(2);
			accountToUpdate.setBalance(accountToUpdate.getBalance()+ transactionAmount);
			return appTransactionRepository.save(appTransaction);
					}
		return null;//"your amount must be >0";
	}

	// method to withdraw money from AppAccount to BankAccount
	public AppTransaction savewithdraw(String bankAccountNumber) {
		Float transactionAmount = (float) 50.00;

		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.commission * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = new BankAccount();
		if (bankAccount.getBalance() >= transactionAmount + transactionFees) {
			bankAccount.setBalance( 800- transactionAmount - transactionFees);//bankAccount.getBalance()
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.transactiondate);
			appTransaction.setOperationType("withdraw");
			appTransaction.setOperationDescription("withdraw");
			appTransaction.setReceiverBankAccountNb("FR9999999999999999999999999");
			appTransaction.setSenderBankAccountNb("FR7612341234123412341234124");

			return appTransactionRepository.save(appTransaction);
		}
		return null; // "your account is not sufficiently funded";
	}

//method to find the list of transaction for a user
	public HashMap<Object, ArrayList<TransactionDto>> findListOfTransactionsByEmail(String email) {
		return null;
	}
}