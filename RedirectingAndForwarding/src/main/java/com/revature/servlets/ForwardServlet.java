package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/main");
			
			ServletContext sc = getServletContext();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
