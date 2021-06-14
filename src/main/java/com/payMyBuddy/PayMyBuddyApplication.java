package com.payMyBuddy;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;
import com.payMyBuddy.repository.AppUserRepository;
import com.payMyBuddy.repository.BankAccountRepository;
import com.payMyBuddy.service.AppTransactionService;
import com.payMyBuddy.service.AppUserService;
import com.payMyBuddy.service.BankAccountService;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppTransactionService appTransactionService;

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Iterable<AppUser> appUsers = appUserService.getAppUsers();
//		appUsers.forEach(Appuser -> System.out.println(Appuser.getLastName()));
//
//		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
//		bankAccounts.forEach(BankAccount -> System.out.println(BankAccount.getIban()));
//
//		Iterable<AppTransaction> appTransactions = appTransactionService.getAppTransactions();
//		appTransactions.forEach(AppTransaction -> System.out.println(AppTransaction.getAmount()));

//		 appUserService.addAppUser("jdurand@gmail.com");
//		 appTransactionService.savePayment("FR7612341234123412341234123");
	 //appTransactionService.fundAppAccount("fgarnier@hotmail.com",600.00f);
//		Optional<AppUser> findAppUser = appUserService.getAppUserById(2);
//		AppUser appUser = findAppUser.get();
//		System.out.println(appUser.getLastName());
//		System.out.println(appUser.getIban());
		
//		AppTransaction appTransaction = appTransactionService.savePayment("FR7612341234123412341234123","FR8112341234123412341234124");
//		System.out.println(appTransaction);

//		AppUser appUserByEmail = appUserService.getAppUserByEmail("fgarnier@hotmail.com");
//			System.out.println(appUserByEmail.getEmail());
//		
//		AppUser appUserBypassword = appUserRepository.findByEmailAndPassword("fgarnier@hotmail.com", "azertyuiop");
//		System.out.println(appUserBypassword.getLastName());
//		
//		AppUser user=appUserService.findByEmail("fgarnier@hotmail.com");
//	System.out.println(user.getFirstName());
	
//	AppUser addContact=appUserService.addConnection("fgarnier@hotmail.com", "pmartin@gmail.com");
//	System.out.println("user added");
	
//	ArrayList<AppTransaction> listOfTransactionsByUsername = new ArrayList<>();
//	listOfTransactionsByUsername=appTransactionService.ListOfTransactions("FR7612341234123412341234123");
//	System.out.println(listOfTransactionsByUsername);
//	}
	
	}	
}
