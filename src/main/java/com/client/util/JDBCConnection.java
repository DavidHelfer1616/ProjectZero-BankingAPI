package com.client.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private static Connection conn = null;

	public static Connection getConnection() {

		/*
		 * to establish a connection we need 3 credentials: url (endpoint), username,
		 * password
		 */

		if (conn == null) {
			// Establish connection
			String endpoint = "bankapp.cmoh7gahadis.us-east-2.rds.amazonaws.com";

			// URL Format (postgres JDBC):
			// jdbc:postgresql://[endpoint]/[database]
			String url = "jdbc:postgresql://" + endpoint + "/postgres";
			String username = "david";
			String password = "password";

			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return conn;
	}

}
