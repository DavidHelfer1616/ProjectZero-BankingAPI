package com.client.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.client.model.Account;
import com.client.util.JDBCConnection;

public class AccountRepoDBImpl implements AccountRepo {

	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public Account getAccount(int accNum) {

		String sql = "SELECT * FROM accounts WHERE acc_num = ?";

		try {

			// Set up PreparesStatement
			PreparedStatement ps = conn.prepareStatement(sql);
			// Set values for Placeholders
			ps.setInt(1, accNum);

			// Execute Query, store results
			ResultSet rs = ps.executeQuery();

			// Extract results
			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Account> getAllAccounts() {
		String sql = "SELECT * FROM accounts";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			List<Account> accounts = new ArrayList<Account>();
			while (rs.next()) {
				// Add each client to the List
				accounts.add(buildAccount(rs));
			}
			return accounts;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account addAccount(Account a) {
		String sql = "INSERT INTO accounts VALUES (default,?,?) RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			// set values for placeholders
			ps.setInt(1, a.getClientId());
			ps.setDouble(2, a.getBalance());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account updateAccount(Account update) {
		String sql = "UPDATE accounts SET client_id=?, balance=? WHERE acc_num = ? RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, update.getClientId());
			ps.setDouble(2, update.getBalance());
			ps.setInt(3, update.getAccNum());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account deleteAccount(int accNum) {

		String sql = "DELETE FROM accounts WHERE acc_num = ? RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, accNum);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Helper Method
	private Account buildAccount(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setAccNum(rs.getInt("acc_num"));
		a.setClientId(rs.getInt("client_id"));
		a.setBalance(rs.getDouble("balance"));
		return a;
	}

}
