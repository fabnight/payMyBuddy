package com.payMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

}
