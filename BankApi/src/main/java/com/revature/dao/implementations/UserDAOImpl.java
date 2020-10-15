package com.revature.dao.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.interfaces.GenericDAO;
import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ConnectionService;

public class UserDAOImpl implements GenericDAO<User> {

	public void create(User t) {
		try {
			//TODO get role id and add that
			//check if accounts in accounts exist and update the users_accounts table accordingly
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"insert into users (username, password, first_name, last_name, email, roleId) "
					+ "values (?, ?, ?, ?, ?, ?)");
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			//ps.setInt(6, t.getRole().getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User getUser(User t) {
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
			ps.setInt(1, t.getUserId());
			
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
				role.setRole(rs.getString("role"));
				user.setRole(role);
				account.setAccountId(rs.getInt("accountId"));
				status.setStatus(rs.getString("status"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
			}
			while (rs.next()) {
				account.setAccountId(rs.getInt("accountId"));
				status.setStatus(rs.getString("status"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
			}
			
			user.setAccounts(accounts);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//no user found
		return null;
	}

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
				role.setRole(rs.getString("role"));
				user.setRole(role);
				account.setAccountId(rs.getInt("accountId"));
				status.setStatus(rs.getString("status"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
			}
			while (rs.next()) {
				account.setAccountId(rs.getInt("accountId"));
				status.setStatus(rs.getString("status"));
				type.setType(rs.getString("type"));
				account.setStatus(status);
				account.setType(type);
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
			}
			
			user.setAccounts(accounts);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There were 0 records returned
		return null;
	}

	public void update(User t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement("UPDATE users"
					+ " SET username = ?, password = ?, first_name = ?,"
					+ " last_name = ?, email = ?, roleId = ?"
					+ " WHERE id = ?;");
			
			//Get info
			ps.setString(1, t.getFirstName());
			ps.setString(2, t.getFirstName());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getFirstName());
			ps.setString(5, t.getLastName());
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

	public void delete(User t) {
		//TODO this should also delete accounts and update users_accounts
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"delete from users where id = ?;");
			ps.setInt(1, t.getUserId());
			PreparedStatement ps2 = ConnectionService.getConnection().prepareStatement(
					"delete from users_accounts where id = ?;");
			ps.setInt(1, t.getUserId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> getAll() {
		//TODO this needs to check for users having multiple accounts
//		List<User> residents = new ArrayList<User>();
//		
//		try {
//			PreparedStatement ps = connection.prepareStatement("select * from users as u "
//					+ "join roles as r on u.roleid = r.id "
//					+ "left join users_accounts as ua on u.id = ua.userid "
//					+ "left join accounts as a on ua.accountid = a.id "
//					+ "left join accountstatuses as ast on a.statusid = ast.id "s
//					+ "left join accounttypes as aty on a.typeid = aty.id;");
//			
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				User user = new user();
//				user.setId(rs.getInt("residents.id"));
//				user.setFirstName(rs.getString("first_name"));
//				user.setLastName(rs.getString("last_name"));
//				
//				// Get the columns pertaining to the apartment
//				// and set the apartment
//				Apartment apartment = new Apartment();
//				apartment.setId(rs.getInt("apartments.id"));
//				apartment.setBuildingLetter(rs.getString("building_letter"));
//				apartment.setRoomNumber(rs.getInt("room_number"));
//				apartment.setMonthlyRent(rs.getDouble("monthly_rent"));
//				
//				resident.setApartment(apartment);
//				
//				residents.add(resident);
//			}
//			
//			return residents;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
	}

}
