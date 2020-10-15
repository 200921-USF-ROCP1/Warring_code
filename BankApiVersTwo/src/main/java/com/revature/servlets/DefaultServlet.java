package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.implementations.AccountDaoImpl;
import com.revature.dao.implementations.UserDaoImpl;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AdminService;
import com.revature.services.ConnectionService;
import com.revature.services.MiscService;

public class DefaultServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(404);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI(); // /*/*/*
		String[] parts = path.split("/"); // [ "", "*", "*", "*" ]
		
		User user;
		Account account;
		String s;
		String str;
		String line = null;
		
		String[] keyPairs;
		
		JsonNode jsonObject;
		
		HashMap<String, Double> jsonData = new HashMap<String, Double>();
		
		AccountDaoImpl accountDao = new AccountDaoImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		
		HttpSession session;
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printwriter = response.getWriter();
		
		StringBuffer sb = new StringBuffer();
		BufferedReader reader;
		
		switch(parts[2]) {
		case "login":
			session = request.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				user = mapper.readValue(request.getInputStream(), User.class);
				
				if (AdminService.verifyCredentialsLogin(user.getUsername(), user.getPassword())) {
					User tempUser = userDao.getByUsername(user.getUsername());
					user.setRole(tempUser.getRole());
					user.setUserId(tempUser.getUserId());
					user.setPassword(null);
					session = request.getSession();
					session.setAttribute("user", user);

					s = mapper.writeValueAsString(userDao.getByUsername(user.getUsername()));
					
					response.setContentType("application/json");
					jsonObject = mapper.readTree(s);
					printwriter.print(jsonObject);
					printwriter.flush();
				} else {
					response.setStatus(400);
					response.setContentType("application/json");
					// Get the printwriter object from response to write the required json object to the output stream      
					// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				    jsonObject = mapper.readTree("{\"message\": \"Invalid Credentials\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
				}
			} else {
				response.setStatus(400);
				response.setContentType("application/json");
				jsonObject = mapper.readTree("{\"message\": \"A user is already logged in\"}");
				printwriter.print(jsonObject);
				printwriter.flush();
			}
			break;
		case "logout":
			session = request.getSession(false);
			if (session != null && session.getAttribute("user") != null) {
				user = (User) session.getAttribute("user");
				response.setContentType("application/json");
				jsonObject = mapper.readTree("{\"message\": \"You have successfully logged out " 
				+ user.getUsername() + "\"}");
				printwriter.print(jsonObject);
				printwriter.flush();
				
				session.removeAttribute("user");
				session.invalidate();
			} else {
				response.setStatus(400);
				response.setContentType("application/json");
				jsonObject = mapper.readTree("{\"message\": \"There was no user logged into the session\"}");
				printwriter.print(jsonObject);
				printwriter.flush();
			}
			break;
		case "register":
			//verify information
			//create user or return error response
			session = request.getSession(false);
			user = null;
			if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
			if (user != null && AdminService.getAccessLevel(user.getRole().getRole()) >= 3) {
				user = mapper.readValue(request.getInputStream(), User.class);
				
				if (AdminService.verifyCredentialsRegister(user.getUsername(), user.getEmail())) {
					userDao = new UserDaoImpl();
					userDao.create(user);
					
					response.setStatus(201);
					response.setContentType("application/json");
					
					s = mapper.writeValueAsString(userDao.getByUsername(user.getUsername()));
					
					jsonObject = mapper.readTree(s);
					printwriter.print(jsonObject);
					printwriter.flush();
				} else {
					response.setStatus(400);
					response.setContentType("application/json");
					// Get the printwriter object from response to write the required json object to the output stream      
					// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
				    jsonObject = mapper.readTree("{\"message\": \"Invalid fields\"}");
					printwriter.print(jsonObject);
					printwriter.flush();
				}
			} else {
				response.setStatus(AdminService.getSecurityStatus());
				response.setContentType("application/json");
				
				jsonObject = mapper.readTree(AdminService.getSecurityMessage());
				printwriter.print(jsonObject);
				printwriter.flush();
			}
			break;
		default:
			response.setStatus(404);
			break;
		}
		
	}
}
