package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("localhost:8080/RedirectingAndForwarding/main");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
