package com.revature.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.interfaces.GenericDAO;
import com.revature.models.Apartment;
import com.revature.models.Resident;
import com.revature.services.ConnectionService;

public class ResidentDAOGenericImpl implements GenericDAO<Resident> {

	public void create(Resident t) {
		try {
			if (t.getApartment() != null) {
				PreparedStatement ps = ConnectionService.getConnection().prepareStatement("SELECT * FROM apartments WHERE id = ?;");
				ps.setInt(1, t.getApartment().getId());

				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					// If there's no rows in the ResultSet, we have to create the apartment
					// apartmentDao.createApartment(resident.getApartment());

					PreparedStatement apartmentStatement = ConnectionService.getConnection().prepareStatement(
							"INSERT INTO apartments " + "(building_letter, room_number, monthly_rent) VALUES (?, ?, ?);",
							Statement.RETURN_GENERATED_KEYS);
					apartmentStatement.setString(1, t.getApartment().getBuildingLetter());
					apartmentStatement.setInt(2, t.getApartment().getRoomNumber());
					apartmentStatement.setDouble(3, t.getApartment().getMonthlyRent());
					apartmentStatement.executeUpdate();

					// Get the generated keys ResultSet
					ResultSet keys = apartmentStatement.getGeneratedKeys();
					keys.next();
					t.getApartment().setId(keys.getInt(1));
				}
			}

			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"INSERT INTO residents (first_name, last_name, apartment_id) " + "VALUES (?, ?, ?);");
			ps.setString(1, t.getFirstName());
			ps.setString(2, t.getLastName());
			ps.setInt(3, t.getApartment().getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Resident get(int id) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement("SELECT * FROM residents WHERE id = ?;");
			ps.setInt(1, id);

			// We use executeQuery because it is a DQL command.
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("id"));
				resident.setFirstName(rs.getString("first_name"));
				resident.setLastName(rs.getString("last_name"));
				// resident.setApartmentId(rs.getInt("apartment_id"));

				// We need to get the Apartment from the database
				// Normally this would go in its own DAO, in a getApartment() method
				// Then I would call apartmentDao.getApartment(rs.getInt("apartment_id"));
				PreparedStatement apartmentStatement = ConnectionService.getConnection()
						.prepareStatement("SELECT * FROM apartments WHERE id  ?");
				apartmentStatement.setInt(1, rs.getInt("apartment_id"));

				ResultSet apartmentRs = apartmentStatement.executeQuery();
				if (apartmentRs.next()) {
					Apartment apartment = new Apartment();
					apartment.setId(apartmentRs.getInt("id"));
					apartment.setBuildingLetter(apartmentRs.getString("building_letter"));
					apartment.setRoomNumber(apartmentRs.getInt("room_number"));
					apartment.setMonthlyRent(apartmentRs.getDouble("monthly_rent"));

					resident.setApartment(apartment);
				}

				return resident;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// There were 0 records returned
		return null;
	}

	public void update(Resident t) {
		try {
			// In order to update the record, we will use the UPDATE command
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"UPDATE residents" + " SET first_name = ?, last_name = ?, apartment_id = ?" + " WHERE id = ?;");

			// Because we have four ?s, we need for sets
			ps.setString(1, t.getFirstName());
			ps.setString(2, t.getLastName());
			ps.setInt(3, t.getApartment().getId());
			ps.setInt(4, t.getId());

			// We use executeUpdate because it is a DML command
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete(Resident t) {
		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement("DELETE FROM residents WHERE id = ?;");
			ps.setInt(1, t.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Resident> getAll() {
		List<Resident> residents = new ArrayList<Resident>();

		try {
			PreparedStatement ps = ConnectionService.getConnection().prepareStatement(
					"SELECT * FROM residents " + "LEFT JOIN apartments on residents.apartment_id = apartments.id;");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Resident resident = new Resident();
				resident.setId(rs.getInt("residents.id"));
				resident.setFirstName(rs.getString("first_name"));
				resident.setLastName(rs.getString("last_name"));

				// Get the columns pertaining to the apartment
				// and set the apartment
				Apartment apartment = new Apartment();
				apartment.setId(rs.getInt("apartment_id"));
				apartment.setBuildingLetter(rs.getString("building_letter"));
				apartment.setRoomNumber(rs.getInt("room_number"));
				apartment.setMonthlyRent(rs.getDouble("monthly_rent"));

				resident.setApartment(apartment);

				residents.add(resident);
			}

			return residents;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}