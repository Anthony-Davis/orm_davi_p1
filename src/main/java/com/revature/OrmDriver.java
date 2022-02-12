package com.revature;

import com.revature.util.DbConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class OrmDriver {
	
	public static void main(String[] args) {
		//create connection for a server installed in localhost, with a user "root" with no password
		try (Connection conn = DbConnector.getConnection()) {
			System.out.println("Connection established");
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
