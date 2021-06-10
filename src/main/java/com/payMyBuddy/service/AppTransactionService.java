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
	private BankAccountService bankAccountService;

	@Autowired
	private BankAccountDto bankAccountDto;

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
			System.out.println("pas assez de crédit, il vous reste " + senderBankAccount.getBalance());
		return null;
	}

	// method to provide money from BankAccount to AppAccount
	public AppTransaction fundAppAccount(String iban) {

		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
		Float transactionAmount = (float) 600.00;

		if (transactionAmount != null) {
			bankAccount.setBalance(bankAccount.getBalance() + transactionAmount);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees((float) 0.00);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("fund");
			appTransaction.setOperationDescription("fund");
			appTransaction.setReceiverBankAccountNb("FR7612341234123412341234124");
			appTransaction.setSenderBankAccountNb("FR9999999999999999999999999");
			// Optional<BankAccount> accountToUpdate = bankAccountRepository.findById(2);
			// accountToUpdate.setBalance(accountToUpdate.getBalance()+ transactionAmount);
			bankAccountRepository.save(bankAccount);
			return appTransactionRepository.save(appTransaction);
		} else
			System.out.println("saisissez un montant positif");
		return null;
	}

	// method to withdraw money from AppAccount to BankAccount
	public AppTransaction savewithdraw(String iban) {
		Float transactionAmount = (float) 50.00;
		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);
		AppTransaction appTransaction = new AppTransaction();
		BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
//check if bankAmount balance is >= transactionAMount+tansactionFees

		if (bankAccount.getBalance() >= transactionAmount + transactionFees) {
			bankAccount.setBalance(bankAccount.getBalance() - transactionAmount - transactionFees);
			appTransaction.setAmount(transactionAmount);
			appTransaction.setFees(transactionFees);
			appTransaction.setOperationDate(TransactionConstants.TRANSACTIONDATE);
			appTransaction.setOperationType("withdraw");
			appTransaction.setOperationDescription("withdraw");
			appTransaction.setReceiverBankAccountNb("FR9999999999999999999999999");
			appTransaction.setSenderBankAccountNb("FR7612341234123412341234124");
			bankAccountRepository.save(bankAccount);
			return appTransactionRepository.save(appTransaction);
		} else
			System.out.println("pas assez de crédit, il vous reste " + bankAccount.getBalance());
		return null;
	}

//method to find the list of transaction for a user
	public HashMap<Object, ArrayList<TransactionDto>> findListOfTransactionsByEmail(String email) {
		return null;
	}
}