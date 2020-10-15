package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* A Front Controller is a singluar servlet that acts as the router
 * for all requests.
 * Technically, you could get away with only having this one servlet
 * and just handling the requests with your own services or classes.
 * You could also route reques to oher servlets if you wanted to.
 */
public class FrontControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI(); // /FrontController/MyServlet/1
		String[] parts = path.split("/"); // [ "", "FrontControler", "MyServlet", "1" ]
		
		// /cars/:id, /users/:id. I want to call CarDAOImpl.get(id) for one, and userDAOImpl.get(id) for the other
		switch(parts[2]) {
		case "cars":
			// I could call a CarService here and pass request and response to deal with them there
			// I could also get the data from the request and pass them to my DAO here
			break;
		case "users":
			break;
		default:
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
