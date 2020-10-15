package com.revature.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ConnectionService;

public class UsersAccountsDaoImpl {
	//This class only needs to be able to create
	//and delete links between users and accounts in
	//the users_accounts table, because of this
	//it does not extend genericDao
	
	public void create(int userId, int accountId) {
		PreparedStatement ps;
		try {
			ps = ConnectionService.getConnection().prepareStatement(
					"insert into users_accounts (userId, accountId) "
					+ "values (?, ?)");
			
			ps.setInt(1, userId);
			ps.setInt(2, accountId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getByIds(int userId, int accountId) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select * from users_accounts as ua "
							+ "where ua.userid = ? and ua.accountid = ?;");
			ps.setInt(1, userId);
			ps.setInt(2, accountId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//no records
		return false;
	}
	
	public void delete(int userId, int accountId) {
		PreparedStatement ps;
		try {
			ps = ConnectionService.getConnection().prepareStatement(
					"delete from users_accounts where userId = ? and accountId = ?;");
			
			ps.setInt(1, userId);
			ps.setInt(2, accountId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
