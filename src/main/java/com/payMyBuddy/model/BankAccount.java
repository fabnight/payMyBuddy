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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "iban", columnNames = { "iban" }) })
public class BankAccount {

	@Id
	@Column(name = "user_id")
	private int id;

	@Column(length = 27)
	@Size(min = 27, max = 27)
	@NotNull
	@NotEmpty(message = "User's iban cannot be empty.")
	private String iban;

	@Column(length = 20)
	private Float balance;

	@Column(length = 80)
	@NotNull
	private String holder;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private AppUser appUser;

	@ManyToMany(mappedBy = "bankAccounts")
	private List<AppTransaction> appTransactions = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
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
