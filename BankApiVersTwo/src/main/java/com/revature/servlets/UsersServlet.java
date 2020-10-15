package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.implementations.UserDaoImpl;
import com.revature.models.User;
import com.revature.services.AdminService;
import com.revature.services.MiscService;

public class UsersServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI(); // /users/*/*
		String[] parts = path.split("/"); // [ "", "users", "*", "*" ]
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter printwriter = response.getWriter();
		
		UserDaoImpl userDao = new UserDaoImpl();
		
		JsonNode jsonObject;
		
		//check for logged in user
		HttpSession session = request.getSession(false);
		User user = null;
		if (session != null && session.getAttribute("user") != null) user = (User) session.getAttribute("user");
		
		if (parts.length == 3) { //the request is get all (/users is the path)
			if (user != null && AdminService.getAccessLevel(user.getRole().getRole()) >= 2) {
				List<User> users = userDao.getAll();
	
				String s = mapper.writeValueAsString(users);
				
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
		} else if (MiscService.isInt(parts[3])) { //the request is get by id (/users/:id is the path)
			if (user != null && (AdminService.getAccessLevel(user.getRole().getRole()) >= 2 || user.getUserId() == Integer.parseInt(parts[3]))) {
				int userId = Integer.parseInt(parts[3]);
				user = userDao.getById(userId);
				
				String s = mapper.writeValueAsString(user);
				
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
		} else {
			response.setStatus(404);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(request.getInputStream(), User.class);
		PrintWriter printwriter = response.getWriter();
		
		UserDaoImpl userDao = new UserDaoImpl();
		
		//check for logged in users
		HttpSession session = request.getSession(false);
		User sessionUser = null;
		if (session != null && session.getAttribute("user") != null) sessionUser = (User) session.getAttribute("user");
		//the path is /users -- the call is for update user
		if (sessionUser != null && (AdminService.getAccessLevel(sessionUser.getRole().getRole()) >= 3 || user.getUserId() == user.getUserId())) { //the request is get by id (/users/:id is the path)

			userDao.update(user);
			
			session.removeAttribute("user");
			User tempUser = userDao.getByUsername(user.getUsername());
			User savedUser = new User();
			tempUser.setPassword(null);
			tempUser.setAccounts(null);
			session = request.getSession();
			session.setAttribute("user", user);
			
			//this can pass the user given in the request back to the user to save a call to the databases
			//however, there may be value in passing back the actual entry in the database
			String s = mapper.writeValueAsString(userDao.getById(user.getUserId()));
			
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
