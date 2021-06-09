package com.payMyBuddy.model;

import java.math.BigDecimal;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 27)
	private String Iban;

	@Column(length = 20)
	private Float balance;

	@Column(length = 80)
	private String holder;

	@ManyToMany(mappedBy = "bankAccounts")
	List<AppUser> appusers = new ArrayList<>();

	@ManyToMany(mappedBy = "bankAccounts")
	
	private List<AppTransaction> appTransactions = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIban() {
		return Iban;
	}

	public void setIban(String iban) {
		Iban = iban;
	}

	public List<AppUser> getAppusers() {
		return appusers;
	}

	public void setAppusers(List<AppUser> appusers) {
		this.appusers = appusers;
	}

	public List<AppTransaction> getAppTransactions() {
		return appTransactions;
	}

	public void setAppTransactions(List<AppTransaction> appTransactions) {
		this.appTransactions = appTransactions;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}
}
