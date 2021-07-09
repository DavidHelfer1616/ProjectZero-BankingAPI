package com.client.repositories;

import java.util.List;

import com.client.model.Account;

public interface AccountRepo {

	public Account getAccount(int accNum);

	public List<Account> getAllAccounts();

	public Account addAccount(Account a);

	public Account updateAccount(Account update);

	public Account deleteAccount(int accNum);

}
