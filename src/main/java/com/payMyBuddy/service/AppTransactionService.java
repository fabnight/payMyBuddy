package com.payMyBuddy.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public Iterable<AppTransaction> getAppTransactions() {
		return appTransactionRepository.findAll();

	}

	// method to transfer money to 3rd party
	public AppTransaction savePayment(String bankAccountNumber) {
		
		AppTransaction appTransaction = new AppTransaction();
		
		Float transactionAmount = (float) 80.00;
		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.commission * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
//		if (bankAccountRepository.getBalance() >= transactionAmount + transactionFees) {
//			bankAccount.setBalance(bankAccount.getBalance() - transactionAmount - transactionFees);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.transactiondate);
			appTransaction.setOperationType(null);
			appTransaction.setOperationDescription("payment");
			appTransaction.setReceiverBankAccountNb(null);
			appTransaction.setSenderBankAccountNb(null);

			return appTransactionRepository.save(appTransaction);
				
			
		}
//		return "your account is not sufficiently funded";
//	}

	// method to provide money from BankAccount to AppAccount
	public String fundAppAccount() {
		
		AppTransaction appTransaction = new AppTransaction();
		Float transactionAmount = null;
//////////
		if (transactionAmount != null) {
			bankAccount.setBalance(bankAccount.getBalance() + transactionAmount);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(0);
			appTransaction.setOperationDate(TransactionConstants.transactiondate);
			appTransaction.setOperationType(null);
			appTransaction.setOperationDescription("fund");
			appTransaction.setReceiverBankAccountNb(null);
			appTransaction.setSenderBankAccountNb(null);

			return null;
		}
		return "your amount must be >0";
	}

	// method to withdraw money from AppAccount to BankAccount
	public String savewithdraw() {
		Float transactionAmount = null;

		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.commission * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
		AppTransaction appTransaction = new AppTransaction();
		if (bankAccount.getBalance() >= transactionAmount + transactionFees) {
			bankAccount.setBalance(bankAccount.getBalance() - transactionAmount - transactionFees);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.transactiondate);
			appTransaction.setOperationType(null);
			appTransaction.setOperationDescription("withdraw");
			appTransaction.setReceiverBankAccountNb(null);
			appTransaction.setSenderBankAccountNb(null);

			return null;
		}
		return "your account is not sufficiently funded";
	}

//method to find the list of transaction for a user
	public HashMap<Object, ArrayList<TransactionDto>> findListOfTransactionsByEmail(String email) {
		return null;
	}
}