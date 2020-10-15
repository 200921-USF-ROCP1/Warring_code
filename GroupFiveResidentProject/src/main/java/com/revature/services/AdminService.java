package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.implementations.CarsDAOGenericImpl;
import com.revature.dao.implementations.PetsDAOGenericImpl;
import com.revature.dao.implementations.ResidentDAOGenericImpl;
import com.revature.models.Apartment;
import com.revature.models.Cars;
import com.revature.models.Pets;
import com.revature.models.Resident;

public class AdminService {
	public static void moveIn(Apartment apartment, List<Resident> residentList, List<Pets> petList) {
		//Given an apartment to move into and a list of the people/pets moving in
		//Update the database to show that the residents/pets live in this apartment
		//(by updating their 'apartment_id' to match the apartment)
		ResidentDAOGenericImpl residentDAO = new ResidentDAOGenericImpl();
		for (Resident resident : residentList) {
			resident.setApartment(apartment);
			residentDAO.update(resident);
		}
		PetsDAOGenericImpl petDAO = new PetsDAOGenericImpl();
		for (Pets pet : petList) {
			pet.setApartment(apartment);
			petDAO.update(pet);
		}
	}

	public static void moveOut(List<Resident> residentList, List<Pets> petList) {
		//Given a list of residents/pets to move out
		//Remove the residents from the database
		//Then find and remove all cars they own (found through cars 'owner_id')
		//Then remove the pets from the database
		ResidentDAOGenericImpl residentDAO = new ResidentDAOGenericImpl();
		CarsDAOGenericImpl carDAO = new CarsDAOGenericImpl();
		for (Resident resident : residentList) {
			residentDAO.delete(resident);
			List<Cars> cars = carDAO.getCarsByOwner(resident);
			for (Cars car : cars) {
				carDAO.delete(car);
			}
		}
		PetsDAOGenericImpl petDAO = new PetsDAOGenericImpl();
		for (Pets pet : petList) {
			petDAO.delete(pet);
		}
		
	}
}
