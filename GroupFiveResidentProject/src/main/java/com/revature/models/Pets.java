package com.revature.models;

public class Pets {
	private int id;
	private String breed;
	private String name;
	private Apartment apartment;
	private Boolean isServiceAnimal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Boolean getIsServiceAnimal() {
		return isServiceAnimal;
	}

	public void setIsServiceAnimal(Boolean isServiceAnimal) {
		this.isServiceAnimal = isServiceAnimal;
	}

}