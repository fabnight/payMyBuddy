package com.payMyBuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.BankAccountRepository;
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
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<AppUser> appUsers = appUserService.getAppUsers();
		appUsers.forEach(Appuser -> System.out.println(Appuser.getLastName()));

		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
		bankAccounts.forEach(BankAccount -> System.out.println(BankAccount.getIban()));

		Iterable<AppTransaction> appTransactions = appTransactionService.getAppTransactions();
		appTransactions.forEach(AppTransaction -> System.out.println(AppTransaction.getAmount()));
		
		//appUserService.addAppUser("jdurand@gmail.com");
		//appTransactionService.savePayment("FR7612341234123412341234123");
		//appTransactionService.fundAppAccount("FR9999999999999999999999999");
		System.out.println(bankAccountRepository.findById(2).get());
				
 
	}

}
