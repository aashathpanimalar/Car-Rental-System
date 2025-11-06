package org.carrental.dao;

import org.carrental.entity.*;
import org.carrental.exception.*;
import java.util.List;

public interface ICarLeaseRepository {

    // ===== Car Management =====
    void addCar(Car car);
    void removeCar(int carID);
    List<Car> listAvailableCars();
    List<Car> listRentedCars();
    Car findCarById(int carID) throws CarNotFoundException;

    // ===== Customer Management =====
    void addCustomer(Customer customer);
    void removeCustomer(int customerID);
    List<Customer> listCustomers();
    Customer findCustomerById(int customerID) throws CustomerNotFoundException;

    // ===== Lease Management =====
    void createLease(int customerID, int carID, int car, Date startDate, Date endDate, String type)
            throws CustomerNotFoundException, CarNotFoundException;

    void returnCar(int leaseID) throws LeaseNotFoundException;

    Lease getLeaseID(int leaseID);

    List<Lease> listActiveLeases();
    List<Lease> listLeaseHistory();

    // ===== Payment Handling =====
    void recordPayment(Lease lease, double amount);
    List<Payment> retrievePaymentHistory(int customerID);
    double calculateTotalRevenue();
}
