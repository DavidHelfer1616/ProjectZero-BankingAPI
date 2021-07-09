package com.client.repositories;

import java.util.List;

import com.client.model.Client;

public interface ClientRepo {

	public Client getClient(int id);

	public List<Client> getAllClients();

	public Client addClient(Client c);

	public Client updateClient(Client update);

	public Client deleteClient(int id);

}
