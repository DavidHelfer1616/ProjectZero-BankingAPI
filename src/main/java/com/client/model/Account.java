package com.client.model;

public class Account {

	private int accNum;
	private int clientId;
	private double balance;

	public Account() {
		super();
	}

	public Account(int accNum, int clientId, double balance) {
		super();
		this.accNum = accNum;
		this.clientId = clientId;
		this.balance = balance;
	}

	public Account(double balance) {
		super();
		this.balance = balance;
	}

	public Account(int accNum, int clientId) {
		super();
		this.accNum = accNum;
		this.clientId = clientId;
	}

	public Account(int accNum) {
		super();
		this.accNum = accNum;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + accNum + ", balance=" + balance + "]";
	}

}
