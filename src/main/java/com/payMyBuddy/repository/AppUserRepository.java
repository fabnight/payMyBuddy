package com.payMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.BankAccount;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
	AppUser findByEmail(String email);

	AppUser findById(int id);

	AppUser findByEmailAndPassword(String email, String password);

	boolean existsAppUserByEmail(String email);
	
	 @Query("SELECT u FROM AppUser u WHERE u.username=:usernameOrEmail OR u.email=:usernameOrEmail")
	    AppUser findByUsernameOrEmail(String usernameOrEmail);
}
