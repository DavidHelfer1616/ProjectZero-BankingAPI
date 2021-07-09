package com.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.client.model.Account;
import com.client.model.Client;
import com.client.model.Transfer;
import com.client.model.WithDepo;
import com.client.services.AccountService;
import com.client.services.ClientService;
import com.google.gson.Gson;

import io.javalin.http.Handler;

public class BankController {

	AccountService as;
	ClientService cs;
	Gson gson = new Gson();

	final static Logger Log = Logger.getLogger(BankController.class);

	public BankController(ClientService cs, AccountService as) {
		this.as = as;
		this.cs = cs;
	}

	public Handler getAllClients = (context) -> {
		List<Client> clients = cs.getAllClients();
		context.result(gson.toJson(clients));
		Log.info("got all clients");
		context.status(200);
	};

	public Handler getClientById = (ctx) -> {
		String input = ctx.pathParam("id");
		int id;
		try {
			id = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			id = -1;
		}
		Client c = cs.getClient(id);
		ctx.result((c != null) ? gson.toJson(c) : "{}");
		if (c != null) {
			Log.info("got client");
			ctx.status(200);
		} else {
			Log.error("did not get client");
			ctx.status(404);
		}
	};

	public Handler addClient = (context) -> {
		Client c = gson.fromJson(context.body(), Client.class);

		c = cs.addClient(c);
		context.result((c != null) ? gson.toJson(c) : "{}");
		Log.info("created client");
		context.status(201);
	};

	public Handler updateClient = (context) -> {
		Client c = gson.fromJson(context.body(), Client.class);
		c = cs.updateClient(c);
		context.result((c != null) ? gson.toJson(c) : "{}");
		if (c != null) {
			Log.info("updated client");
			context.status(200);
		} else {
			Log.error("did not update client");
			context.status(404);
		}
	};

	public Handler deleteClient = (context) -> {
		String input = context.pathParam("id");
		int id;
		try {
			id = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			id = -1;
		}

		Client c = cs.deleteClient(id);
		context.result((c != null) ? gson.toJson(c) : "{}");
		if (c != null) {
			Log.info("deleted client");
			context.status(204);
		} else {
			Log.error("did not delete client");
			context.status(404);
		}
	};

	public Handler clientTransaction = (context) -> {

		String json = context.body();
		System.out.println(json);

		Log.info("successful transaction");
		context.status(204);

	};

	public Handler getAllAccounts = (ctx) -> {
		String input = ctx.pathParam("id");
		int id;
		try {
			id = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			id = -1;
		}

		double high = Double.parseDouble(ctx.queryParam("amountLessThan", "1000000"));
		double low = Double.parseDouble(ctx.queryParam("amountGreaterThan", "0"));

		List<Account> accounts = as.getAllAccounts();
		List<Account> accountRange = new ArrayList<Account>();
		for (Account a : accounts) {
			double balance = a.getBalance();
			if (id == a.getClientId()) {
				if (balance >= low && balance <= high) {
					accountRange.add(a);
				}
			}
		}
		ctx.result(gson.toJson(accountRange));
		if (!accountRange.isEmpty()) {
			Log.info("got accounts");
			ctx.status(200);
		} else {
			Log.error("did not get accounts");
			ctx.status(404);
		}
	};

	public Handler getAccountById = (ctx) -> {
		String input = ctx.pathParam("acc_num");
		int accNum;
		try {
			accNum = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			accNum = -1;
		}
		Account a = as.getAccount(accNum);
		ctx.result((a != null) ? gson.toJson(a) : "{}");
		if (a != null) {
			Log.info("got account");
			ctx.status(200);
		} else {
			Log.error("did not get account");
			ctx.status(404);
		}
	};

	public Handler addAccount = (ctx) -> {
		Account a = gson.fromJson(ctx.body(), Account.class);

		a = as.addAccount(a);
		ctx.result((a != null) ? gson.toJson(a) : "{}");
		Log.info("created account");
		ctx.status(201);
	};

	public Handler updateAccount = (ctx) -> {
		String input = ctx.pathParam("acc_num");
		int accNum;
		try {
			accNum = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			accNum = -1;
		}
		String input2 = ctx.pathParam("id");
		int id;
		try {
			id = Integer.parseInt(input2);
		} catch (NumberFormatException e) {
			id = -2;
		}
		Account a = gson.fromJson(ctx.body(), Account.class);
		a.setAccNum(accNum);
		a.setClientId(id);
		a = as.updateAccount(a);

		ctx.result((a != null) ? gson.toJson(a) : "{}");
		if (a != null) {
			Log.info("updated account");
			ctx.status(200);
		} else {
			Log.error("did not update account");
			ctx.status(404);
		}
	};

	public Handler deleteAccount = (ctx) -> {
		String input = ctx.pathParam("acc_num");
		int accNum;
		try {
			accNum = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			accNum = -1;
		}

		Account a = as.deleteAccount(accNum);
		ctx.result((a != null) ? gson.toJson(a) : "{}");
		if (a != null) {
			Log.info("deleted account");
			ctx.status(204);
		} else {
			Log.error("did not delete account");
			ctx.status(404);
		}
	};

	public Handler withdrawDeposit = (ctx) -> {
		String input = ctx.pathParam("acc_num");
		int accNum;
		try {
			accNum = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			accNum = -1;
		}
		Account a = as.getAccount(accNum);

		WithDepo w = gson.fromJson(ctx.body(), WithDepo.class);

		if (w.getDeposit() == 0.0) {
			if ((a.getBalance() - w.getWithdraw()) < 0) {
				ctx.result((a != null) ? gson.toJson(a) : "{}");
				Log.error("insufficient funds");
				ctx.status(422);
			} else {
				double balance = a.getBalance() - w.getWithdraw();
				a.setBalance(balance);
				a = as.updateAccount(a);
				ctx.result((a != null) ? gson.toJson(a) : "{}");
				Log.info("successfully withdrew");
				ctx.status(200);
			}
		} else {
			double balance = a.getBalance() + w.getDeposit();
			a.setBalance(balance);
			a = as.updateAccount(a);
			ctx.result((a != null) ? gson.toJson(a) : "{}");
			Log.info("successful deposit");
			ctx.status(200);
		}

	};

	public Handler transfer = (ctx) -> {
		String input = ctx.pathParam("acc_num");
		int accNum;
		try {
			accNum = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			accNum = -1;
		}
		String input2 = ctx.pathParam("acc_num2");
		int accNum2;
		try {
			accNum2 = Integer.parseInt(input2);
		} catch (NumberFormatException e) {
			accNum2 = -1;
		}

		Account giver = as.getAccount(accNum);
		Account receiver = as.getAccount(accNum2);

		Transfer t = gson.fromJson(ctx.body(), Transfer.class);
		double check = giver.getBalance() - t.getAmount();
		if (check < 0) {
			ctx.result((giver != null) ? gson.toJson(giver) : "{}");
			Log.error("insufficient funds");
			ctx.status(422);
		} else {
			giver.setBalance(giver.getBalance() - t.getAmount());
			as.updateAccount(giver);
			receiver.setBalance(receiver.getBalance() + t.getAmount());
			as.updateAccount(receiver);

			ctx.result((giver != null) ? gson.toJson(giver) : "{}");
			Log.info("succesful Transfer");
			ctx.status(200);
		}

	};

	public Handler accountTransaction = (ctx) -> {

		String json = ctx.body();
		System.out.println(json);
		Log.info("succesful transaction");
		ctx.status(204);

	};
}
