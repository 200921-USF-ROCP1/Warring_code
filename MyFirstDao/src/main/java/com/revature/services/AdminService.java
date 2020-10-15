package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.implementations.ResidentDAOImpl;
import com.revature.models.Resident;

public class AdminService {
	public void moveIn(List<Resident> residentList, List<Pet> petList) {
		//add above list to apartment
		// 1. add apartment id to residents in residentList
		// 2. add apartment id to pets in petList
	}
	
	public void moveOut(List<Resident> residentList, List<Pet> petList) {
		//remove list from apartment
		// 1. remove apartment id from residents in resident list
		// step through resident list, change resident objects apartment to null, update resident through dao
		// 2. remove apartment id from pets in resident list
		// step through pet list, change pet objects apartment to null, update pet through dao
		for (Resident resident : residentList) {
			resident.setApartment(null);
			ResidentDAOImpl.update(resident);
		}
	}
}
