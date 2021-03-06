package com.payMyBuddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.constants.TransactionConstants;
import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppTransactionRepository;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;

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
		//find appUser per email
		AppUser appUserByUserName = appUserRepository.findByEmail(username);
		//find bankAccount per User
		BankAccount senderBankAccount = bankAccountService.getBankAccountByIban(appUserByUserName.getIban());
		//find connection appUser by email
		AppUser appUserConnection = appUserService.findByEmail(receiverBankAccountNb);
		// find its bankAccount per User
		BankAccount receiverBankAccount = bankAccountService.getBankAccountByIban(appUserConnection.getIban());
		//calcul fees from transaction amount
		Float transactionFees = (float) (Math.round(transactionAmount * TransactionConstants.COMMISSION * 100.0)
				/ 100.0);
		// check validity of amount vs balance
		if (transactionAmount != null && transactionAmount > 0
				&& senderBankAccount.getBalance() >= transactionAmount + transactionFees) {
			//appTransaction set
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
			throw new Exception("Not enough credit available. Due = " + due + "???, " + "missing = " + missing + "???");
		}

		return appTransaction;
	}

	// method to withdraw money from AppAccount to BankAccount
	public Float savewithdraw(String username, Float transactionAmount) throws Exception {
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

			throw new Exception("Not enough credit available. Due = " + due + "???, " + "missing = " + missing + "???");

		}
		Float newBalance = bankAccountService.balance(user.getIban());
		return newBalance;
	}

	// method to provide money from BankAccount to AppAccount
	public Float fundAppAccount(String username, Float transactionAmount) throws Exception {
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
			appTransactionRepository.save(appTransaction);
		} else {
			throw new Exception("Transaction failed");
		}
		Float newBalance = bankAccountService.balance(user.getIban());
		return newBalance;

	}

//method to find the list of transactions per Iban for a user

	public List<AppTransaction> ListOfTransactions(String username) {
		AppUser user = appUserService.findByEmail(username);
		String iban = user.getIban();
		List<AppTransaction> listOfTransactionsByIban = new ArrayList<>();
		listOfTransactionsByIban = appTransactionRepository.findByReceiverBankAccountNbOrSenderBankAccountNb(iban);

		return listOfTransactionsByIban;
	}

	// method to find the list of transactions per BankAccount for a user

	public List<AppTransaction> ListOfTransactionsId(String username) {
		AppUser user = appUserService.findByEmail(username);
		int id = user.getUserId();
		List<AppTransaction> listOfTransactionsById = new ArrayList<>();
		listOfTransactionsById = appTransactionRepository.findByReceiverIdOrSenderId(id);

		return listOfTransactionsById;
	}
}