package com.client.services;

import java.util.List;

import com.client.model.Account;
import com.client.model.Client;

public interface AccountService {

	// CRUD operations

	public Account getAccount(int accNum);

	public List<Account> getAllAccounts();

	public Account addAccount(Account a);

	public Account updateAccount(Account update);

	public Account deleteAccount(int accNum);

	// Special services

	public Account setClientId(Account a, Client b);

	public Account deposit(Account a, double deposit);

	public Account withdraw(Account a, double withdraw);

	public Account tansfer(Account give, Account receive, double transfer);

	public List<Account> getAccountRange(double low, double high);
}
