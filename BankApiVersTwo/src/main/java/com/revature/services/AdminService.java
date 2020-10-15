package com.revature.services;

import java.util.List;

import com.revature.dao.implementations.UserDaoImpl;
import com.revature.models.User;

public class AdminService {

	//this accesses the userDao to verify user login information
	public static boolean verifyCredentialsLogin(String username, String password) {
		//find user information
		//verify info
		UserDaoImpl userDao = new UserDaoImpl();
		User tempUser = userDao.getByUsername(username);
		if (tempUser == null) return false; //user doesnt exist
		if (tempUser.getUsername() != null && tempUser.getUsername().equals(username) && tempUser.getPassword().equals(password)) return true;
		else return false;
	}
	
	//this accesses the userDao to verify that the username and email provided are not in use
	public static boolean verifyCredentialsRegister(String username, String email) {
		UserDaoImpl userDao = new UserDaoImpl();
		List<User> users = userDao.getByRegisterCredentials(username, email);
		if (users.size() == 0) return true;
		else return false;
	}
	
	//this returns the message to be shown when a user tries to access something they dont have credentials for
	public static String getSecurityMessage() {
		return "{\"message\": \"The requested action is not permitted\"}";
	}
	
	//this returns the status code to be retruned when a user tries to access something they dont have credentials for
	public static int getSecurityStatus() {
		return 401;
	}
	
	//returns the access level of the given role
	public static int getAccessLevel(String role) { 
		switch (role.toLowerCase()) {
		case "standard":
			return 0;
		case "premium":
			return 1;
		case "employee":
			return 2;
		case "admin":
			return 3;
		default: //role not found
			return 0;
		}
	}
}
