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

public class UserDaoImpl implements GenericDao<User> {
	
	@Override
	public int create(User t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"insert into users (username, password, first_name, last_name, email, roleId) "
					+ "values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().getRoleId());
			
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
		return 0; //no user was created
	}

	@Override
	public User getById(int id) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select * from users as u "
							+ "join roles as r on u.roleid = r.id "
							+ "left join users_accounts as ua on u.id = ua.userid "
							+ "left join accounts as a on ua.accountid = a.id "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "where u.id = ?;");
			ps.setInt(1, id);
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();
			User user = new User();
			Role role = new Role();
			Account account = new Account();
			AccountStatus status = new AccountStatus();
			AccountType type = new AccountType();
			List<Account> accounts = new ArrayList<Account>();
			if (rs.next()) {
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				role.setRoleId(rs.getInt("roleid"));
				role.setRole(rs.getString("role"));
				user.setRole(role);
			}
			do {
				account = new Account();
				account.setAccountId(rs.getInt("accountId"));
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
				if (account.getAccountId() == 0) {accounts=null;break;} //break isnt nessecary here - there should *never* be a situation where there are multiple accounts and one has an id of 0
			} while (rs.next());
			
			user.setAccounts(accounts);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}
	
	public User getByUsername(String username) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select * from users as u "
							+ "join roles as r on u.roleid = r.id "
							+ "left join users_accounts as ua on u.id = ua.userid "
							+ "left join accounts as a on ua.accountid = a.id "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "where u.username = ?;");
			ps.setString(1, username);
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();
			User user = new User();
			Role role = new Role();
			Account account = new Account();
			AccountStatus status = new AccountStatus();
			AccountType type = new AccountType();
			List<Account> accounts = new ArrayList<Account>();
			if (rs.next()) {
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				role.setRoleId(rs.getInt("roleid"));
				role.setRole(rs.getString("role"));
				user.setRole(role);
			}
			do {
				account = new Account();
				account.setAccountId(rs.getInt("accountId"));
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
				if (account.getAccountId() == 0) {accounts=null;break;} //break isnt nessecary here - there should *never* be a situation where there are multiple accounts and one has an id of 0
			} while (rs.next());
			
			user.setAccounts(accounts);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}
	
	public List<User> getByRegisterCredentials(String username, String email) {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select * from users as u "
							+ "join roles as r on u.roleid = r.id "
							+ "left join users_accounts as ua on u.id = ua.userid "
							+ "left join accounts as a on ua.accountid = a.id "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "where u.username = ? or u.email = ?;");
			ps.setString(1, username);
			ps.setString(2, email);
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();

			User prevUser = null;
			List<User> users = new ArrayList<User>();
			List<Account> accounts = new ArrayList<Account>();
			while (rs.next()) {
				//reset the references
				//this is done to prevent the array lists from containing duplicate values
				User user = new User();
				Role role = new Role();
				Account account = new Account();
				AccountStatus status = new AccountStatus();
				AccountType type = new AccountType();
				
				//get user data
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				
				role.setRoleId(rs.getInt("roleid"));
				role.setRole(rs.getString("role"));
				user.setRole(role);
				
				if (!user.equals(prevUser)) accounts = new ArrayList<Account>();
				
				//get the account information for the current user
				account.setAccountId(rs.getInt("accountId"));
				account.setBalance(rs.getDouble("balance"));
				
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				account.setStatus(status);
				
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setType(type);
				
				accounts.add(account);
				
				if (account.getAccountId() == 0) accounts = null;
				
				user.setAccounts(accounts);
				
				//if the user is different add them to users
				//otherwise replace the users account field
				// -- the only field that will be different
				//with the new accounts ArrayList
				// -- it should have a new account added to it
				if (!user.equals(prevUser)) users.add(user);
				else users.get(prevUser.getUserId()-1).setAccounts(accounts);
				//save the current user
				prevUser = user;
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}

	@Override
	public void update(User t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement("UPDATE users"
					+ " SET username = ?, password = ?, first_name = ?,"
					+ " last_name = ?, email = ?, roleId = ?"
					+ " WHERE id = ?;");
			
			//Get info
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole().getRoleId());
			//What user do we update
			ps.setInt(7, t.getUserId());
			
			// We use executeUpdate because it is a DML command
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User t) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<User> getAll() {
		try {
			PreparedStatement ps = 
					ConnectionService.getConnection().prepareStatement(
							"select * from users as u "
							+ "join roles as r on u.roleid = r.id "
							+ "left join users_accounts as ua on u.id = ua.userid "
							+ "left join accounts as a on ua.accountid = a.id "
							+ "left join accountstatuses as ast on a.statusid = ast.id "
							+ "left join accounttypes as aty on a.typeid = aty.id "
							+ "order by u.id;");
			
			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();

			User prevUser = null;
			List<User> users = new ArrayList<User>();
			List<Account> accounts = new ArrayList<Account>();
			while (rs.next()) {
				//reset the references
				//this is done to prevent the array lists from containing duplicate values
				User user = new User();
				Role role = new Role();
				Account account = new Account();
				AccountStatus status = new AccountStatus();
				AccountType type = new AccountType();
				
				//get user data
				user.setUserId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				
				role.setRoleId(rs.getInt("roleid"));
				role.setRole(rs.getString("role"));
				user.setRole(role);
				
				if (!user.equals(prevUser)) accounts = new ArrayList<Account>();
				
				//get the account information for the current user
				account.setAccountId(rs.getInt("accountId"));
				account.setBalance(rs.getDouble("balance"));
				
				status.setStatusId(rs.getInt("statusid"));
				status.setStatus(rs.getString("status"));
				account.setStatus(status);
				
				type.setTypeId(rs.getInt("typeid"));
				type.setType(rs.getString("type"));
				account.setType(type);
				
				accounts.add(account);
				
				if (account.getAccountId() == 0) accounts = null;
				
				user.setAccounts(accounts);
				
				//if the user is different add them to users
				//otherwise replace the users account field
				// -- the only field that will be different
				//with the new accounts ArrayList
				// -- it should have a new account added to it
				if (!user.equals(prevUser)) users.add(user);
				else users.get(prevUser.getUserId()-1).setAccounts(accounts);
				//save the current user
				prevUser = user;
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();;
		}
		//there were 0 records returned
		return null;
	}

}
