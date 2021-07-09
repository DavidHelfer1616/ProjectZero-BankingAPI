package com.junit.tests;

import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.client.repositories.AccountRepoDBImpl;
import com.client.repositories.ClientRepoDBImpl;
import com.client.services.AccountServiceImpl;
import com.client.services.ClientServiceImpl;

public class ServiceDAOTests {

	static AccountRepoDBImpl account;
	static AccountServiceImpl accServ;
	static ClientRepoDBImpl client;
	static ClientServiceImpl cliServ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		account = new AccountRepoDBImpl();
		client = new ClientRepoDBImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearin it down");
	}

	@Test
	public void getClientTest() {
		assertNull(client);
	}

}
