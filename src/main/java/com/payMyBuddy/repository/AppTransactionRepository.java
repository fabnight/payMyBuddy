package com.payMyBuddy.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;

@Repository
public interface AppTransactionRepository extends CrudRepository<AppTransaction, Integer>  {
	AppTransaction findById(int id);

	@Query("SELECT u FROM AppTransaction u WHERE u.receiverBankAccountNb=:receiverBankAccountNbOrsenderBankAccountNb OR u.senderBankAccountNb=:receiverBankAccountNbOrsenderBankAccountNb")
	List<AppTransaction> findByReceiverBankAccountNbOrSenderBankAccountNb(String receiverBankAccountNbOrsenderBankAccountNb);
}
