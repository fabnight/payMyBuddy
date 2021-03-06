package com.payMyBuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private BankAccount bankAccount;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<AppUser> userContacts = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	public AppUser() {

	}

	@Column(length = 80)
	private String lastName;

	@Column(length = 80)
	private String firstName;

	@Column(name = "username", length = 65)
	private String username;

	@Column(length = 115, unique = true)
	@NotEmpty(message = "User's Email cannot be empty.")
	private String email;

	@Column(length = 80)
	@NotNull
	@NotEmpty(message = "User's password cannot be empty.")
	private String password = "a";

	@Column(length = 27)
	@Size(min = 27, max = 27)
	@NotNull
	@NotEmpty(message = "User's iban cannot be empty.")
	private String iban;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public List<AppUser> getUserContacts() {
		return userContacts;
	}

	public void setUserContacts(List<AppUser> userContacts) {
		this.userContacts = userContacts;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
