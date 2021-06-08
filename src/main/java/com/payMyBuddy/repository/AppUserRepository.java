package com.payMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByEmail(String email);
	AppUser findById(int id);
	AppUser findByEmailAndPassword(String email, String password);

}
