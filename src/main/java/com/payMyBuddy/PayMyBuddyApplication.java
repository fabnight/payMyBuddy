package com.payMyBuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.service.AppTransactionService;
import com.payMyBuddy.service.AppUserService;
import com.payMyBuddy.service.BankAccountService;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private AppTransactionService appTransactionService;
	@Autowired
	private BankAccountService bankAccountService;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<AppUser> appUsers = appUserService.getUsers();
		appUsers.forEach(Appuser -> System.out.println(Appuser.getLastName()));

		Iterable<AppTransaction> appTransactions = appTransactionService.getAppTransactions();
		appTransactions.forEach(AppTransaction -> System.out.println(AppTransaction.getAmount()));

		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
		bankAccounts.forEach(BankAccount -> System.out.println(BankAccount.getBankAccountNb()));
	}

}
