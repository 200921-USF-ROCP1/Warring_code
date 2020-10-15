package com.revature.models;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
	private List<Account> accounts;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != User.class) return false;
		User user = (User) obj;
		return this.userId == user.getUserId() && this.username.equals(user.getUsername())
				&& this.password.equals(user.getPassword()) && this.firstName.equals(user.getFirstName())
				&& this.lastName.equals(user.getLastName()) && this.lastName.equals(user.getLastName())
				&& this.email.equals(user.getEmail()) && this.role.equals(user.getRole());
	}
	
	@Override
	public String toString() {
		String accountsString = ""; //TODO make accountsString get accounts
		return userId + " " + username + " " + password + " " + firstName + " " + lastName
				+ " " + email + " " + role + " " + accountsString;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}