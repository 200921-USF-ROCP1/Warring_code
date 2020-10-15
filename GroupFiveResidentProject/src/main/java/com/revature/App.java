package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.dao.implementations.ResidentDAOImpl;
import com.revature.dao.interfaces.ResidentDao;
import com.revature.models.Resident;
import com.revature.services.ConnectionService;

public class App {
	public static void main(String[] args) {
		
		try {		
			
//			ResidentDao residentDao = new ResidentDAOImpl();
//			Resident res = new Resident();
//			res.setFirstName("Ressy");
//			res.setLastName("Ressison");
//			
//			residentDao.createResident(res);
			
			//ArrayList<Resident> residents = new ArrayList<Resident>();
			//residents.add(res);
			
			System.out.println("test");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionService.closeConnection();
		}
	}
}