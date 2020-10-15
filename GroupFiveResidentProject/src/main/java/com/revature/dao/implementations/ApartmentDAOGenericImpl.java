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

public class ApartmentDAOGenericImpl implements GenericDAO<Apartment> {

	Connection connection;

	public ApartmentDAOGenericImpl() {
		this.connection = ConnectionService.getConnection();
	}

	public void create(Apartment t) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO apartments " + "(id, building_letter, room_number, monthly_rent) VALUES (?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getBuildingLetter());
			ps.setInt(3, t.getRoomNumber());
			ps.setDouble(4, t.getMonthlyRent());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Apartment get(int id) {
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM apartments WHERE id = ?;");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Apartment apartment = new Apartment();
				apartment.setId(rs.getInt("id"));
				apartment.setBuildingLetter(rs.getString("building_letter"));
				apartment.setRoomNumber(rs.getInt("room_number"));
				apartment.setMonthlyRent(rs.getDouble("monthly_rent"));

				return apartment;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void update(Apartment t) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE apartments"
					+ " SET building_letter = ?, room_number = ?, monthly_rent = ?" + " WHERE id = ?;");
			ps.setString(1, t.getBuildingLetter());
			ps.setInt(2, t.getRoomNumber());
			ps.setDouble(3, t.getMonthlyRent());
			ps.setInt(4, t.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(Apartment t) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM apartments" + " WHERE id = ?;");

			ps.setInt(1, t.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Apartment> getAll() {
		// TODO Auto-generated method stub
		List<Apartment> apartments = new ArrayList<Apartment>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM apartments;");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Apartment apartment = new Apartment();
				apartment.setId(rs.getInt("apartments.id"));
				apartment.setBuildingLetter(rs.getString("building_letter"));
				apartment.setRoomNumber(rs.getInt("room_number"));
				apartment.setMonthlyRent(rs.getDouble("monthly_rent"));

				apartments.add(apartment);
			}

			return apartments;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

	}
}