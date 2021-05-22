package com.payMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.repository.AppUserRepository;

@Service
public class AppUserService {
	@Autowired
	private AppUserRepository appUserRepository;

	public Iterable<AppUser> getUsers() {
		return appUserRepository.findAll();

	}

}
