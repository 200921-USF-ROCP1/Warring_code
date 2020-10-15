package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      // Set response content type so it will always render to HTML
	      response.setContentType("text/html");
	      
	     int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	     //you might have to move line 19 (exception type) into the (exception != null) if statement
	     // -- this may crash if the exception is null
	     String exceptionType = ((Class) request.getAttribute("javax.servlet.error.exception_type")).toString();
	     Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
	     String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
	     
	     PrintWriter pw = response.getWriter();
	     
	     pw.print("<html><body>");
	     if (exception != null) {
	    	 pw.println("<p>Exception type: " + exceptionType + "</p>");
	    	 pw.println("<p>Exception message: " + exception.getMessage() + "</p>");
	    	 
	    	 pw.println("<div>Exception statck trace: <ul>");
	    	 
	    	 StackTraceElement[] stackTrace = exception.getStackTrace();
	    	 for (int i = 0; i < stackTrace.length; ++i) {
	    		 pw.print("<li>" + stackTrace[i] + "</li>");
	    	 }
	    	 pw.print("</ul> </div>");
	     }
	     
	     pw.print("<p>Status code; " + statusCode + "</p>");
	     pw.print("<p>Request URI: " + requestUri + "</p>");
	     
	     pw.print("</body></html>");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}