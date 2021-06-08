package com.payMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppTransaction;
import com.payMyBuddy.model.BankAccount;

@Repository
public interface AppTransactionRepository extends CrudRepository<AppTransaction, Integer>  {
	AppTransaction findById(int id);

}
