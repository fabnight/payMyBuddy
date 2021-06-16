package com.payMyBuddy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

	// method to transfer money to 3rd party
	public AppTransaction savePayment(String senderIban, String receiverIban) {

		AppTransaction appTransaction = new AppTransaction();
		BankAccount senderBankAccount = bankAccountService.getBankAccountByIban(senderIban);
		BankAccount receiverBankAccount = bankAccountService.getBankAccountByIban(receiverIban);
		Float transactionAmount = 90f;
		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);

		if (senderBankAccount.getBalance() >= transactionAmount + transactionFees) {
			senderBankAccount.setBalance(senderBankAccount.getBalance() - transactionAmount - transactionFees);
			receiverBankAccount.setBalance(receiverBankAccount.getBalance() + transactionAmount);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("payment");
			appTransaction.setOperationDescription("payment");
			appTransaction.setReceiverBankAccountNb("FR8112341234123412341234124");
			appTransaction.setSenderBankAccountNb("FR7612341234123412341234123");
			bankAccountRepository.save(senderBankAccount);
			bankAccountRepository.save(receiverBankAccount);
			return appTransactionRepository.save(appTransaction);
		} else
			System.out.println("pas assez de crédit, il vous reste " + senderBankAccount.getBalance() + "€");
		return null;
	}

	// method to provide money from BankAccount to AppAccount
	public AppTransaction fundAppAccount(String username, Float transactionAmount) {
		AppUser user = appUserService.findByEmail(username);
		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(user.getIban());

		if (transactionAmount != null&&transactionAmount>0) {
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
			System.out.println("NEW BALANCE=" + bankAccount.getBalance() + "€");
			return appTransactionRepository.save(appTransaction);
		} else
			System.out.println("saisissez un montant positif");
		return null;
	}

	// method to withdraw money from AppAccount to BankAccount
	public AppTransaction savewithdraw(String username, Float transactionAmount) {
		AppUser user = appUserService.findByEmail(username);
		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(user.getIban());

		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);

//check if bankAmount balance is >= transactionAMount+tansactionFees
		if (bankAccount.getBalance() >= transactionAmount + transactionFees&&transactionAmount != null&&transactionAmount>0) {
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
			System.out.println("NEW BALANCE=" + bankAccount.getBalance() + "€");
			return appTransactionRepository.save(appTransaction);
		} else
			System.out.println("pas assez de crédit, il vous reste " + bankAccount.getBalance() + "€");
		return null;
	}

//method to find the list of transactions for a user

	public List<AppTransaction> ListOfTransactions(String username) {
		AppUser user = appUserService.findByEmail(username);
		String iban = user.getIban();
		List<AppTransaction> listOfTransactionsByIban = new ArrayList<>();
		listOfTransactionsByIban = appTransactionRepository.findByReceiverBankAccountNbOrSenderBankAccountNb(iban);
		listOfTransactionsByIban.getClass();
		return listOfTransactionsByIban;
	}
}