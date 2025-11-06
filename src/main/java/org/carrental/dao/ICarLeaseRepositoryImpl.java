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
        String sql = "INSERT INTO vehicle(carID,make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?,?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,car.getCarID());
            ps.setString(2, car.getMake());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setDouble(5, car.getDailyRate());
            ps.setString(6, car.getStatus());
            ps.setInt(7, car.getPassengerCapacity());
            ps.setInt(8, car.getEngineCapacity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(int carID) {
        String sql = "DELETE FROM vehicle WHERE carID=?";
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
                rs.getInt("carID"),
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
        String sql = "SELECT * FROM vehicle WHERE carID=?";
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

    // ===== Lease Management =====
    @Override
    public void createLease(int leaseID,int customerID, int carID, Date startDate, Date endDate, String type)
            throws CustomerNotFoundException, CarNotFoundException {

        findCustomerById(customerID);
        findCarById(carID);

        String sql = "INSERT INTO lease(leaseID, customerID,carID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leaseID);
            ps.setInt(2, customerID);
            ps.setInt(3,carID);
            ps.setDate(4, startDate);
            ps.setDate(5, endDate);
            ps.setString(6, type);
            ps.executeUpdate();

            String update = "UPDATE vehicle SET status='notAvailable' WHERE carID=?";
            try (PreparedStatement ps2 = conn.prepareStatement(update)) {
                ps2.setInt(1, carID);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnCar(int leaseID) throws LeaseNotFoundException {
        String sql = "UPDATE vehicle v JOIN lease l ON v.carID=l.carID SET v.status='available' WHERE l.leaseID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leaseID);
            int updated = ps.executeUpdate();
            if (updated == 0)
                throw new LeaseNotFoundException("Lease ID " + leaseID + " not found");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Lease getLeaseID(int leaseID) {
        String sql = "SELECT * FROM lease WHERE leaseID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, leaseID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Lease(
                            rs.getInt("leaseID"),
                            rs.getInt("customerID"),
                            rs.getInt("carID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> list = new ArrayList<>();
        String sql = "SELECT * FROM lease";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("customerID"),
                        rs.getInt("carID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Lease> listLeaseHistory() {
        return listActiveLeases(); // for now, same output (can later filter by date)
    }

    //===== Payment Handling =====
     @Override
    public void recordPayment(Lease lease, double amt) {
        String sql = "INSERT INTO payment(leaseID, paymentDate, amount) VALUES (?, NOW(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lease.getLeaseID());
            ps.setDouble(2, amt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> retrievePaymentHistory(int customerID) {
        List<Payment> payments = new ArrayList<>();
        String sql = """
            SELECT p.paymentID, p.leaseID, p.paymentDate, p.amount
            FROM payment p
            JOIN lease l ON p.leaseID = l.leaseID
            WHERE l.customerID = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("paymentID"),
                        rs.getInt("leaseID"),
                        rs.getDate("paymentDate"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public double calculateTotalRevenue() {
        String sql = "SELECT SUM(amount) AS total FROM payment";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
