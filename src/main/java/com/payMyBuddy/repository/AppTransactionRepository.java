package com.payMyBuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppTransaction;

@Repository
public interface AppTransactionRepository extends CrudRepository<AppTransaction, Integer>  {
	AppTransaction findById(int id);

	//to find a list of transactions relating to a specific Iban
	@Query("SELECT u FROM AppTransaction u WHERE u.receiverBankAccountNb=:receiverBankAccountNbOrsenderBankAccountNb OR u.senderBankAccountNb=:receiverBankAccountNbOrsenderBankAccountNb")
	List<AppTransaction> findByReceiverBankAccountNbOrSenderBankAccountNb(String receiverBankAccountNbOrsenderBankAccountNb);
	
	//to find a list of transactions relating to a specific bankAccount
	@Query("SELECT u FROM AppTransaction u WHERE u.receiverId=:receiverIdOrsenderId OR u.senderId=:receiverIdOrsenderId")
	List<AppTransaction> findByReceiverIdOrSenderId(int receiverIdOrsenderId);
}
