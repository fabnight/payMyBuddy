package com.payMyBuddy.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class AppTransaction {
	@ManyToMany
			//(mappedBy = "appTransactions")
	List<BankAccount> bankAccounts = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@Column
	private String senderBankAccountNb;

	@Column
	private String receiverBankAccountNb;

	@Column
	private String operationDescription;
	
	@Column
	private String operationType;
	
	@Column
	private Date operationDate;

	@Column
	private Float amount;
	
	@Column
	private Float fees;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getSenderBankAccountNb() {
		return senderBankAccountNb;
	}

	public void setSenderBankAccountNb(String senderBankAccountNb) {
		this.senderBankAccountNb = senderBankAccountNb;
	}

	public String getReceiverBankAccountNb() {
		return receiverBankAccountNb;
	}

	public void setReceiverBankAccountNb(String receiverBankAccountNb) {
		this.receiverBankAccountNb = receiverBankAccountNb;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
//	public List<BankAccount> getBankAccounts() {
//		return bankAccounts;
//	}
//
//	public void setBankAccounts(List<BankAccount> bankAccounts) {
//		this.bankAccounts = bankAccounts;
//	}

	public Float getFees() {
		return fees;
	}

	public void setFees(Float fees) {
		this.fees = fees;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date transactiondate) {
		this.operationDate = transactiondate;
	}
}
