package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.implementations.AccountDaoImpl;
import com.revature.dao.implementations.UserDaoImpl;
import com.revature.dao.implementations.UsersAccountsDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AdminService;
import com.revature.services.MiscService;

public class AccountsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI(); // /users/*/*
		String[] parts = path.split("/"); // [ "", "users", "*", "*" ]
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printwriter = response.getWriter();
		AccountDaoImpl accountDao = new AccountDaoImpl();
		
		//check for logged in users
		HttpSession session = request.getSession(false);
		User user = null;
		if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
		if (parts.length == 3) { //the request is get all (/accounts is the path)
			if (user != null && AdminService.getAccessLevel(user.getRole().getRole()) >= 2) {
	
				List<Account> accounts = accountDao.getAll();
				
				String s = mapper.writeValueAsString(accounts);
				
				response.setContentType("application/json");
				JsonNode jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				JsonNode jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts.length == 4 && MiscService.isInt(parts[3])){ //the request is get by id
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2) || user.getUserId() == Integer.parseInt(parts[3])) {
				Account account = accountDao.getById(Integer.parseInt(parts[3]));
				
				String s = mapper.writeValueAsString(account);
				
				response.setContentType("application/json");
				JsonNode jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				JsonNode jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts[3].equals("status") && MiscService.isInt(parts[4])) { //find accounts by status
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2)) {
				List<Account> accounts = accountDao.getAccountByStatus(Integer.parseInt(parts[4]));
				
				String s = mapper.writeValueAsString(accounts);
				
				response.setContentType("application/json");
				JsonNode jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				JsonNode jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts[3].equals("owner") && MiscService.isInt(parts[4])) { //find accounts by user
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2)) {
				UserDaoImpl userDao = new UserDaoImpl();
				
				List<Account> accounts = userDao.getById(Integer.parseInt(parts[4])).getAccounts();
				
				String s = mapper.writeValueAsString(accounts);
				
				response.setContentType("application/json");
				JsonNode jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				JsonNode jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else {
			response.setStatus(404);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI(); // /users/*/*
		String[] parts = path.split("/"); // [ "", "users", "*", "*" ]
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printwriter = response.getWriter();
		AccountDaoImpl accountDao = new AccountDaoImpl();
		UsersAccountsDaoImpl usersAccountsDao = new UsersAccountsDaoImpl();
		
		User user;
		Account account;
		String s;
		String str;
		String line = null;
		
		String[] keyPairs;
		
		JsonNode jsonObject;
		
		HashMap<String, Double> jsonData = new HashMap<String, Double>();
		
		//UserDaoImpl userDao = new UserDaoImpl();
		StringBuffer sb = new StringBuffer();
		BufferedReader reader;
		
		HttpSession session = request.getSession(false);
		user = null;
		if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
		if (parts.length == 3) { //create account for self
			if (user != null) {
				//get account to add
				account = mapper.readValue(request.getInputStream(), Account.class);
				
				//add account to database and linking table
				account.setAccountId(accountDao.create(account));
				usersAccountsDao.create(user.getUserId(), account.getAccountId());
				
				//return account
				response.setStatus(201);
				s = mapper.writeValueAsString(account);
				
				response.setContentType("application/json");
				jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (MiscService.isInt(parts[3])) { //create account for id
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2 || user.getUserId() == Integer.parseInt(parts[3]))) {
				//get account to add
				account = mapper.readValue(request.getInputStream(), Account.class);
				
				//add account to database and linking table
				account.setAccountId(accountDao.create(account));
				usersAccountsDao.create(Integer.parseInt(parts[3]), account.getAccountId());
				
				//return account
				response.setStatus(201);
				s = mapper.writeValueAsString(account);
				
				response.setContentType("application/json");
				jsonObject = mapper.readTree(s);
				printwriter.print(jsonObject);
				printwriter.flush();
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts[3].equals("withdraw")) {
			reader = request.getReader();
			session = request.getSession(false);
			user = null;
			if (session != null) user = (User) session.getAttribute("user");
			
			//read input
			while ((line = reader.readLine()) != null) {
				if (line != null) sb.append(line);
			}
			
			//convert input to key/value pairs
			str = sb.toString();
			str = str.replaceAll("\\s", "").replace("{", "").replace("\"", "").replace("}", "");
			keyPairs = str.split(",");
			  
			//add pairs to hashmap
			jsonData.clear();
			for (int i = 0; i < keyPairs.length; i++) {
				String[] temp = keyPairs[i].split(":");
				jsonData.put(temp[0], Double.parseDouble(temp[1]));
			}
			
			//check for logged in user and appropriate access level
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 3 || (MiscService.isOwner(user.getUserId(), jsonData.get("accountId").intValue())))) {
				//withdraw money from account
				accountDao = new AccountDaoImpl();
				account = accountDao.getById(jsonData.get("accountId").intValue()); //get account from db
				if (account.getBalance() - jsonData.get("amount") >= 0) account.setBalance(account.getBalance() - jsonData.get("amount")); //remove amount from account balance
				else {
					response.setStatus(400);
					response.setContentType("application/json");
					jsonObject = mapper.readTree("{\"message\": \"Funds Not Available\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
					return;
				}
				accountDao.update(account); //update account in db
				
				//return data to user
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree("{\"message\": \"$" + jsonData.get("amount") + " has been withdrawn "
						+ "from Account #" + jsonData.get("accountId").intValue() + "\"}");
				printwriter.print(jsonObject);
				printwriter.flush();
			} else { //user does not have access
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts[3].equals("deposit")) {
			reader = request.getReader();
			session = request.getSession(false);
			user = null;
			if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
			
			//read input
			while ((line = reader.readLine()) != null) {
				if (line != null) sb.append(line);
			}
			
			//convert input to key/value pairs
			str = sb.toString();
			str = str.replaceAll("\\s", "").replace("{", "").replace("\"", "").replace("}", "");
			keyPairs = str.split(",");
			  
			//add pairs to hashmap
			jsonData.clear();
			for (int i = 0; i < keyPairs.length; i++) {
				String[] temp = keyPairs[i].split(":");
				jsonData.put(temp[0], Double.parseDouble(temp[1]));
			}
			
			//check for logged in user and appropriate access level
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 3 || (MiscService.isOwner(user.getUserId(), jsonData.get("accountId").intValue())))) {

				//deposit money to account
				account = accountDao.getById(jsonData.get("accountId").intValue()); //get account from db
				if (jsonData.get("amount") > 0) account.setBalance(account.getBalance() + jsonData.get("amount")); //remove amount from account balance
				else {
					response.setStatus(400);
					response.setContentType("application/json");
					jsonObject = mapper.readTree("{\"message\": \"Must Deposit Positve Amount\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
					return;
				}
				accountDao.update(account); //update account in db
				
				//return data to user
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree("{\"message\": \"$" + jsonData.get("amount") + " has been deposited "
						+ "to Account #" + jsonData.get("accountId").intValue() + "\"}");
				
				printwriter.print(jsonObject);
				printwriter.flush();
			} else { //user does not have access
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else if (parts[3].equals("transfer")) {
			reader = request.getReader();
			session = request.getSession(false);
			user = null;
			if (session != null) user = (User) session.getAttribute("user");
			
			//read input
			while ((line = reader.readLine()) != null) {
				if (line != null) sb.append(line);
			}
			
			//convert input to key/value pairs
			str = sb.toString();
			str = str.replaceAll("\\s", "").replace("{", "").replace("\"", "").replace("}", "");
			keyPairs = str.split(",");
			  
			//add pairs to hashmap
			jsonData.clear();
			for (int i = 0; i < keyPairs.length; i++) {
				String[] temp = keyPairs[i].split(":");
				jsonData.put(temp[0], Double.parseDouble(temp[1]));
			}
			
			//check for logged in user and appropriate access level
			if (user != null && MiscService.doesAccountExist(jsonData.get("targetAccountId").intValue()) && (AdminService.getAccessLevel(user.getRole().getRole()) >= 3 || (MiscService.isOwner(user.getUserId(), jsonData.get("sourceAccountId").intValue())))) {
				accountDao = new AccountDaoImpl();
				account = accountDao.getById(jsonData.get("sourceAccountId").intValue()); //get account from db
				if (account.getBalance() - jsonData.get("amount") >= 0) account.setBalance(account.getBalance() - jsonData.get("amount")); //remove amount from account balance
				else {
					response.setStatus(400);
					response.setContentType("application/json");
					jsonObject = mapper.readTree("{\"message\": \"Funds Not Available\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
					return;
				}
				accountDao.update(account); //update account in db
				
				//deposit money to account
				account = accountDao.getById(jsonData.get("targetAccountId").intValue()); //get account from db
				if (jsonData.get("amount") > 0) account.setBalance(account.getBalance() + jsonData.get("amount")); //remove amount from account balance
				else {
					response.setStatus(400);
					response.setContentType("application/json");
					jsonObject = mapper.readTree("{\"message\": \"Must Deposit Positve Amount\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
					return;
				}
				accountDao.update(account); //update account in db
				
				//return data to user
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree("{\"message\": \"$" + jsonData.get("amount") + " has been transfered "
						+ "from Account #" + jsonData.get("sourceAccountId").intValue() + " to Account #" + jsonData.get("targetAccountId").intValue() + "\"}");
				
				printwriter.print(jsonObject);
				printwriter.flush();
			} else { //user does not have access
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
		} else {
			response.setStatus(404);
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Account account = mapper.readValue(request.getInputStream(), Account.class);
		PrintWriter printwriter = response.getWriter();
		
		HttpSession session = request.getSession(false);
		User user = null;
		if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
		//the path is /accounts -- the function is to update account
		if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2)) {
			AccountDaoImpl accountDao = new AccountDaoImpl();
				
			accountDao.update(account);
			
			//this can pass the account given in the request back to the user to save a call to the databases
			//however, there may be value in passing back the actual entry in the database
			String s = mapper.writeValueAsString(accountDao.getById(account.getAccountId()));
			
			response.setContentType("application/json");
			JsonNode jsonObject = mapper.readTree(s);
			printwriter.print(jsonObject);
			printwriter.flush();
		} else {
			response.setStatus(AdminService.getSecurityStatus());
			response.setContentType("application/json");
			
			JsonNode jsonObject = mapper.readTree(AdminService.getSecurityMessage());
			printwriter.print(jsonObject);
			printwriter.flush();
		}
	}
}
