package com.batch.model;


public class Customer {
	private String accountNumber;
	private String trxAmount;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public String getTrx_time() {
		return trx_time;
	}
	public void setTrx_time(String trx_time) {
		this.trx_time = trx_time;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Customer(String accountNumber, String trxAmount, String description, String trxDate, String trx_time,
			String customerId) {
		super();
		this.accountNumber = accountNumber;
		this.trxAmount = trxAmount;
		this.description = description;
		this.trxDate = trxDate;
		this.trx_time = trx_time;
		this.customerId = customerId;
	}
	private String description;
	private String trxDate;
	private String trx_time;
	private String customerId;
	@Override
	public String toString() {
		return "Customer [accountNumber=" + accountNumber + ", trxAmount=" + trxAmount + ", description=" + description
				+ ", trxDate=" + trxDate + ", trx_time=" + trx_time + ", customerId=" + customerId + "]";
	}

}
