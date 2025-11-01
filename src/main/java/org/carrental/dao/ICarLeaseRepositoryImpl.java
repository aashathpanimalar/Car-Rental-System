package org.carrental.dao;

import org.carrental.entity.*;
import org.carrental.exception.*;
import org.carrental.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {

    private Connection conn;

    public ICarLeaseRepositoryImpl() {
        conn = DBConnUtil.getConnection();
    }

    // ===== Car Management =====
    @Override
    public void addCar(Car car) {
        String sql = "INSERT INTO vehicle(make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getMake());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getDailyRate());
            ps.setString(5, car.getStatus());
            ps.setInt(6, car.getPassengerCapacity());
            ps.setInt(7, car.getEngineCapacity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(int carID) {
        String sql = "DELETE FROM vehicle WHERE vehicleID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> listAvailableCars() {
        return getCarsByStatus("available");
    }

    @Override
    public List<Car> listRentedCars() {
        return getCarsByStatus("notAvailable");
    }

    private List<Car> getCarsByStatus(String status) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM vehicle WHERE status=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cars.add(mapCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private Car mapCar(ResultSet rs) throws SQLException {
        return new Car(
                rs.getInt("vehicleID"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("dailyRate"),
                rs.getString("status"),
                rs.getInt("passengerCapacity"),
                rs.getInt("engineCapacity")
        );
    }

    @Override
    public Car findCarById(int carID) throws CarNotFoundException {
        String sql = "SELECT * FROM vehicle WHERE vehicleID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapCar(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CarNotFoundException("Car ID " + carID + " not found");
    }

    // ===== Customer Management =====
    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer(firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCustomer(int customerID) {
        String sql = "DELETE FROM customer WHERE customerID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> listCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        String sql = "SELECT * FROM customer WHERE customerID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CustomerNotFoundException("Customer ID " + customerID + " not found");
    }
}
