package com.payMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.BankAccount;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {

}
