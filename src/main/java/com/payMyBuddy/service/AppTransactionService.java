package com.payMyBuddy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.payMyBuddy.dto.BankAccountDto;
import com.payMyBuddy.dto.AppTransactionDto;
import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppTransactionRepository;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;
import com.payMyBuddy.constants.TransactionConstants;

@Transactional(rollbackOn = Exception.class)
@Service
public class AppTransactionService {
	private static final Logger logger = LogManager.getLogger("AppTransactionService");
	@Autowired
	private AppTransactionRepository appTransactionRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppTransactionDto appTransactionDto;

	public Iterable<AppTransaction> getAppTransactions() {
		return appTransactionRepository.findAll();
	}

	public Optional<AppTransaction> getAppTransactionById(Integer id) {
		return appTransactionRepository.findById(id);
	}

	// Method to transfer money to 3rd party
	public AppTransaction savePayment(String username, String receiverBankAccountNb, Float transactionAmount,
			String description) throws Exception {
		AppTransaction appTransaction = new AppTransaction();

		AppUser appUserByUserName = appUserRepository.findByEmail(username);
		BankAccount senderBankAccount = bankAccountService.getBankAccountByIban(appUserByUserName.getIban());
		AppUser appUserConnection = appUserService.findByEmail(receiverBankAccountNb);
		BankAccount receiverBankAccount = bankAccountService.getBankAccountByIban(appUserConnection.getIban());

		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);

		if (transactionAmount != null && transactionAmount > 0
				&& senderBankAccount.getBalance() >= transactionAmount + transactionFees) {
			senderBankAccount.setBalance(senderBankAccount.getBalance() - transactionAmount - transactionFees);
			receiverBankAccount.setBalance(receiverBankAccount.getBalance() + transactionAmount);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("payment");
			appTransaction.setOperationDescription(description);
			appTransaction.setReceiverBankAccountNb(appUserConnection.getIban());
			appTransaction.setReceiverId(appUserConnection.getUserId());
			appTransaction.setSenderId(appUserByUserName.getUserId());
			appTransaction.setSenderBankAccountNb(appUserByUserName.getIban());
			bankAccountRepository.save(senderBankAccount);
			bankAccountRepository.save(receiverBankAccount);
			appTransactionRepository.save(appTransaction);
		} else {
			float due = transactionAmount + transactionFees;
			float missing = due - senderBankAccount.getBalance();
			throw new Exception("Not enough credit available. Due = " + due + "€, " + "missing = " + missing + "€");
		}

		return appTransaction;
	}

	// method to withdraw money from AppAccount to BankAccount
	public AppTransaction savewithdraw(String username, Float transactionAmount) throws Exception {
		AppUser user = appUserService.findByEmail(username);
		AppTransaction appTransaction = new AppTransaction();

		BankAccount bankAccount = bankAccountService.getBankAccountByIban(user.getIban());

		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
		if (bankAccount.getBalance() >= transactionAmount + transactionFees && transactionAmount != null
				&& transactionAmount > 0) {
			bankAccount.setBalance(bankAccount.getBalance() - transactionAmount - transactionFees);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("withdraw");
			appTransaction.setOperationDescription("withdraw");
			appTransaction.setReceiverBankAccountNb(user.getIban());
			appTransaction.setReceiverId(user.getUserId());
			appTransaction.setSenderId(user.getUserId());
			appTransaction.setSenderBankAccountNb(user.getIban());
			bankAccountRepository.save(bankAccount);
			appTransactionRepository.save(appTransaction);
		} else {
			float due = transactionAmount + transactionFees;
			float missing = due - bankAccount.getBalance();
			throw new Exception("Not enough credit available. Due = " + due + "€, " + "missing = " + missing + "€");
		}
		return appTransaction;
	}

	// method to provide money from BankAccount to AppAccount
	public AppTransaction fundAppAccount(String username, Float transactionAmount) throws Exception {
		AppUser user = appUserService.findByEmail(username);
		AppTransaction appTransaction = new AppTransaction();

		BankAccount bankAccount = bankAccountService.getBankAccountByIban(user.getIban());

		if (transactionAmount != null && transactionAmount > 0) {
			bankAccount.setBalance(bankAccount.getBalance() + transactionAmount);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees((float) 0.00);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("fund");
			appTransaction.setOperationDescription("fund");
			appTransaction.setReceiverBankAccountNb(user.getIban());
			appTransaction.setReceiverId(user.getUserId());
			appTransaction.setSenderId(user.getUserId());
			appTransaction.setSenderBankAccountNb(user.getIban());
			bankAccountRepository.save(bankAccount);
			
		} else {
			throw new Exception("Transaction failed");
		}
		return appTransactionRepository.save(appTransaction);
	}

//method to find the list of transactions per Iban for a user

	public List<AppTransaction> ListOfTransactions(String username) {
		AppUser user = appUserService.findByEmail(username);
		String iban = user.getIban();
		List<AppTransaction> listOfTransactionsByIban = new ArrayList<>();
		listOfTransactionsByIban = appTransactionRepository.findByReceiverBankAccountNbOrSenderBankAccountNb(iban);
	
		return listOfTransactionsByIban;
	}
	
	//method to find the list of transactions per BankAccount for a user

		public List<AppTransaction> ListOfTransactionsId(String username) {
			AppUser user = appUserService.findByEmail(username);
			int id = user.getUserId();
			List<AppTransaction> listOfTransactionsById = new ArrayList<>();
			listOfTransactionsById=appTransactionRepository.findByReceiverIdOrSenderId(id);
			
		
			return listOfTransactionsById;
		}
}