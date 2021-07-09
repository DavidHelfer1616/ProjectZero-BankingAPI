package com.client.app;

import com.client.controllers.BankController;
import com.client.repositories.AccountRepo;
import com.client.repositories.AccountRepoDBImpl;
import com.client.repositories.ClientRepo;
import com.client.repositories.ClientRepoDBImpl;
import com.client.services.AccountService;
import com.client.services.AccountServiceImpl;
import com.client.services.ClientService;
import com.client.services.ClientServiceImpl;

import io.javalin.Javalin;

public class BankingApp {

	public static void main(String[] args) {
		// establish our javalin Object
		Javalin app = Javalin.create();

		// Establish the routes/Endpoints Javalin will manage
		establishRoutes(app);

		// run Javalin
		app.start(7001);
	}

	private static void establishRoutes(Javalin app) {
		// here we are going to define a list of routes(endpoints)
		// we want Javalin to manage
		ClientRepo cr = new ClientRepoDBImpl();
		ClientService cs = new ClientServiceImpl(cr);

		AccountRepo ar = new AccountRepoDBImpl();
		AccountService as = new AccountServiceImpl(ar);
		BankController bc = new BankController(cs, as);

		// Establish a route to the landing page
		app.get("/", (ctx) -> ctx.result("This is my Banking App Home page!"));
		app.get("/hello", (ctx) -> ctx.result("Hello World!"));

		app.get("/clients", bc.getAllClients);
		app.get("/clients/:id", bc.getClientById);

		app.post("/clients", bc.addClient);

		app.put("/clients/:id", bc.updateClient);

		app.delete("clients/:id", bc.deleteClient);

		app.patch("/clients/:id", bc.clientTransaction);

		app.get("/clients/:id/accounts", bc.getAllAccounts);
		app.get("/clients/:id/accounts/:acc_num", bc.getAccountById);

		app.post("/clients/:id/accounts", bc.addAccount);

		app.put("/clients/:id/accounts/:acc_num", bc.updateAccount);

		app.delete("/clients/:id/accounts/:acc_num", bc.deleteAccount);

		app.patch("/clients/:id/accounts/:acc_num/transfer/:acc_num2", bc.transfer);

		app.patch("/clients/:id/accounts/:acc_num", bc.withdrawDeposit);

		app.get("/clients/:id/accounts/", bc.getAllAccounts);
	}

}
