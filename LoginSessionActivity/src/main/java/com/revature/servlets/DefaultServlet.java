package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.classes.User;

public class DefaultServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter printwriter = response.getWriter();
		
		if (session != null) {
			User u = (User) session.getAttribute("user");
			printwriter.print(u.getUsername() + " is logged in.");
		} else {
			printwriter.print("no one is logged in");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(request.getInputStream(),User.class);
		PrintWriter printwriter = response.getWriter();
		
		if (user.getUsername().equals("dummyUsername") && user.getPassword().equals("dummyPassword")) {
			user.setPassword("");
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
	}

}
