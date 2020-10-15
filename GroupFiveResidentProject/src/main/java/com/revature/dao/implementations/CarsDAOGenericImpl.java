package com.revature.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.interfaces.GenericDAO;
import com.revature.models.Cars;
import com.revature.models.Resident;
import com.revature.services.ConnectionService;

public class CarsDAOGenericImpl implements GenericDAO<Cars> {
	
	Connection connection;

	public CarsDAOGenericImpl() {
		this.connection = ConnectionService.getConnection();
	}

	public void create(Cars t) {
		try {
			if (t.getResident() != null) {
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM residents WHERE id = ?;");
				ps.setInt(1, t.getResident().getId());

				ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					Resident resident = new Resident();
					ResidentDAOImpl rd = new ResidentDAOImpl();
					rd.createResident(resident);
					
				}
				
			}
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO cars (make, model, year, license_plate, owner_id) " + "Values(?, ?, ?, ?, ?);");
			ps.setString(1, t.getMake());
			ps.setString(2, t.getModel());
			ps.setInt(3, t.getYear());
			ps.setString(4, t.getLicensePlate());
			ps.setInt(5, t.getResident().getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Cars get(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM cars WHERE id = ?;");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Cars car = new Cars();
				car.setId(rs.getInt("id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("year"));
				car.setLicensePlate(rs.getString("license_plate"));
				
				PreparedStatement residentStatement = connection.prepareCall("SELECT * FROM resident WHERE id = ?;");
				residentStatement.setInt(1, rs.getInt("resident_id"));
				
				ResultSet residentRs = residentStatement.executeQuery();
				
				if(residentRs.next()) {
					Resident resident = new Resident();
					ResidentDAOImpl rdi = new ResidentDAOImpl();
					rdi.createResident(resident);
					
					car.setResident(resident);
				}
				
				return car;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void update(Cars t) {
		// TODO Auto-generated method stub
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("UPDATE cars"
					+ " SET make = ?, model = ?, year = ?, license_plate = ?, resident_id = ?"
					+ " WHERE id = ?;");
			
			
			ps.setString(1, t.getMake());
			ps.setString(2, t.getModel());
			ps.setInt(3, t.getYear());
			ps.setString(4, t.getLicensePlate());
			ps.setInt(5, t.getResident().getId());
			ps.setInt(6, t.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void delete(Cars t) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM cars WHERE id = ?;");
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Cars> getAll() {
		// TODO Auto-generated method stub
		List<Cars> cars = new ArrayList<Cars>();
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM cars"
					+ " LEFT JOIN residents on cars.resident_id = residents.id;");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// create the cars
				Cars car = new Cars();
				car.setId(rs.getInt("id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("year"));
				car.setLicensePlate(rs.getString("license_plate"));
			
				
				// create the resident
				Resident resident = new Resident();
				resident.setId(rs.getInt("residents.id"));
				resident.setFirstName(rs.getString("first_name"));
				resident.setLastName(rs.getString("last_name"));
				
				car.setResident(resident);
				
				cars.add(car);
			}
			
			return cars;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Cars> getCarsByOwner(Resident owner) {
		// TODO Auto-generated method stub
				List<Cars> cars = new ArrayList<Cars>();
				
				try {
					PreparedStatement ps = connection.prepareStatement("SELECT * FROM cars WHERE owner_id = ?;");
					ps.setInt(1, owner.getId());
					
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						// create the cars
						Cars car = new Cars();
						car.setId(rs.getInt("id"));
						car.setMake(rs.getString("make"));
						car.setModel(rs.getString("model"));
						car.setYear(rs.getInt("year"));
						car.setLicensePlate(rs.getString("license_plate"));
						
						cars.add(car);
					}
					
					return cars;
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
	}

}