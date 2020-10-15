package com.revature.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.interfaces.GenericDao;
import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ConnectionService;

public class AccountDaoImpl implements GenericDao<Account>{

	public int create(Account t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"insert into accounts (balance, statusid, typeid) "
					+ "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, t.getBalance());
			ps.setInt(2, t.getStatus().getStatusId());
			ps.setInt(3, t.getType().getTypeId());
			
			ps.executeUpdate();
			
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; //no account was made
	}

	@Override
	public Account getById(int id) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select a.*, ast.status, aty.type from accounts as a "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "where a.id = ?;");
			ps.setInt(1, id);
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();
			
			Account account = new Account();
			AccountStatus status = new AccountStatus();
			AccountType type = new AccountType();
			
			if (rs.next()) {
				account.setAccountId(rs.getInt("id"));
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
			}
			
			return account;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}
	
	public List<Account> getAccountByStatus(int statusId) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select a.*, ast.status, aty.type from accounts as a "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "where statusId = ? order by a.id;");
					ps.setInt(1, statusId);
			
			ResultSet rs = ps.executeQuery();
					
			List<Account> accounts = new ArrayList<Account>();
			Account account = new Account();
			AccountStatus status2 = new AccountStatus();
			AccountType type = new AccountType();
			
			while (rs.next()) {
				account.setAccountId(rs.getInt("id"));
				status2.setStatusId(rs.getInt("statusid"));
				status2.setStatus(rs.getString("status"));
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setStatus(status2);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
				account = new Account();
			}
			
			return accounts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//There were 0 records returned
		return null;
	}

	@Override
	public void update(Account t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement("UPDATE accounts"
					+ " SET balance = ?, statusid = ?, typeid = ?"
					+ " WHERE id = ?;");
			
			//Get info
			ps.setDouble(1, t.getBalance());
			ps.setInt(2, t.getStatus().getStatusId());
			ps.setInt(3, t.getType().getTypeId());
			//What account do we update
			ps.setInt(4, t.getAccountId());
			
			// We use executeUpdate because it is a DML command
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Account t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Account> getAll() {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select *, a.id as accountid from accounts as a "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "order by accountid;");
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();
			
			List<Account> accounts = new ArrayList<Account>();
			Account account = new Account();
			AccountStatus status = new AccountStatus();
			AccountType type = new AccountType();
			
			while (rs.next()) {
				account.setAccountId(rs.getInt("accountid"));
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
				account = new Account();
			}
						
			return accounts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}

}
