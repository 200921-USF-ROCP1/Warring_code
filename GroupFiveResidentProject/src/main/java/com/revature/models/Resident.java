package com.revature.models;

public class Resident {
	private int id;
	private String firstName;
	private String lastName;
	
	private Apartment apartment; 
	
	
	public int getId() {
		return id;
	}
	public Apartment getApartment() {
		return apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
//	public int getApartmentId() {
//		return apartmentId;
//	}
//	
//	public void setApartmentId(int apartmentId) {
//		this.apartmentId = apartmentId;
//	}
//	
}