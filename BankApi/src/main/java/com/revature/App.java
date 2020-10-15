package com.revature;

import com.revature.dao.implementations.UserDAOImpl;
import com.revature.services.ConnectionService;

public class App {
	//This class exists only for testing --
	//NO LOGIC SHOULD EXIST IN THIS CLASS EXCEPT WHAT IS ABSOLUTLY NESSECARY FOR TESTING

	public static void main(String[] args) {
			try {		
				//ResidentDao residentDao = new ResidentDAOImpl();
				//Resident res = new Resident();
				//res.setFirstName("Ressy");
				//res.setLastName("Ressison");
				
				//residentDao.createResident(res);
				UserDAOImpl u = new UserDAOImpl();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ConnectionService.closeConnection();
			}
	}
}
