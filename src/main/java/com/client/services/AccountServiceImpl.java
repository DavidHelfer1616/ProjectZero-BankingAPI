package com.client.services;

import java.util.ArrayList;
import java.util.List;

import com.client.model.Account;
import com.client.model.Client;
import com.client.repositories.AccountRepo;
import com.client.repositories.ClientRepo;

public class AccountServiceImpl implements AccountService {

	public AccountRepo ar;

	public ClientRepo cr;

	public AccountServiceImpl(AccountRepo ar) {
		this.ar = ar;
	}

	@Override
	public Account getAccount(int accNum) {
		return ar.getAccount(accNum);
	}

	@Override
	public List<Account> getAllAccounts() {
		return ar.getAllAccounts();
	}

	@Override
	public Account addAccount(Account a) {
		return ar.addAccount(a);
	}

	@Override
	public Account updateAccount(Account update) {
		return ar.updateAccount(update);
	}

	@Override
	public Account deleteAccount(int accNum) {
		return ar.deleteAccount(accNum);
	}

	@Override
	public Account setClientId(Account a, Client b) {
		Client owner = cr.getClient(b.getId());
		Account account = ar.getAccount(a.getAccNum());

		account.setClientId(owner.getId());
		ar.updateAccount(account);

		return account;
	}

	@Override
	public Account deposit(Account a, double deposit) {
		Account account = ar.getAccount(a.getAccNum());
		account.setBalance(account.getBalance() + deposit);
		ar.updateAccount(account);
		return account;
	}

	@Override
	public Account withdraw(Account a, double withdraw) {
		Account account = ar.getAccount(a.getAccNum());
		double check = account.getBalance() - withdraw;
		if (check < 0) {
			return null;
		} else {
			account.setBalance(check);
			ar.updateAccount(account);
			return account;
		}
	}

	@Override
	public Account tansfer(Account give, Account receive, double transfer) {
		Account giver = ar.getAccount(give.getAccNum());
		Account receiver = ar.getAccount(receive.getAccNum());
		double check = giver.getBalance() - transfer;
		if (check < 0) {
			return null;
		} else {
			giver.setBalance(check);
			ar.updateAccount(giver);
			receiver.setBalance(receiver.getBalance() + transfer);
			ar.updateAccount(receiver);
			return giver;
		}
	}

	@Override
	public List<Account> getAccountRange(double low, double high) {
		List<Account> accounts = ar.getAllAccounts();
		List<Account> accountRange = new ArrayList<Account>();

		for (Account a : accounts) {
			double balance = a.getBalance();
			if ((balance >= low) & (balance <= high)) {
				accountRange.add(a);
			}
		}
		return accountRange;
	}

}
