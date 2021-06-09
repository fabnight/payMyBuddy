package com.payMyBuddy.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payMyBuddy.model.AppUser;
import com.payMyBuddy.model.Role;
import com.payMyBuddy.service.AppUserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AppUserService appUserService;
 
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}
 
		AppUser user = appUserService.findByUsernameOrEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	}
	private List<GrantedAuthority> getGrantedAuthorities(AppUser user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Role role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}
	
 
}
