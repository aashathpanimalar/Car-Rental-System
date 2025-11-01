package org.carrental.main;

import org.carrental.dao.*;
import org.carrental.entity.*;
import org.carrental.exception.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ICarLeaseRepository dao = new ICarLeaseRepositoryImpl();
        int choice;

        do {
            System.out.println("\n===== CAR RENTAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Customer Management");
            System.out.println("2. Car Management");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> customerMenu(sc, dao);
                case 2 -> carMenu(sc, dao);
                case 3 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);
    }

    // ---------------- CUSTOMER MENU ----------------
    private static void customerMenu(Scanner sc, ICarLeaseRepository dao) {
        int opt;
        do {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Remove Customer");
            System.out.println("3. List Customers");
            System.out.println("4. Find Customer by ID");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> addCustomer(sc, dao);
                case 2 -> removeCustomer(sc, dao);
                case 3 -> listCustomers(dao);
                case 4 -> findCustomer(sc, dao);
                case 5 -> {}
                default -> System.out.println("Invalid option!");
            }
        } while (opt != 5);
    }

    // ---------------- CAR MENU ----------------
    private static void carMenu(Scanner sc, ICarLeaseRepository dao) {
        int opt;
        do {
            System.out.println("\n--- Car Management ---");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. List Available Cars");
            //System.out.println("4. List Rented Cars");
            System.out.println("5. Find Car by ID");
            System.out.println("6. Back");
            System.out.print("Enter your choice: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> addCar(sc, dao);
                case 2 -> removeCar(sc, dao);
                case 3 -> listAvailableCars(dao);
                case 4 -> listRentedCars(dao);
                case 5 -> findCar(sc, dao);
                case 6 -> {}
                default -> System.out.println("Invalid option!");
            }
        } while (opt != 6);
    }

    // =================== CRUD FUNCTIONS ===================
    private static void addCustomer(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("First name: ");
        String fn = sc.nextLine();
        System.out.print("Last name: ");
        String ln = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        dao.addCustomer(new Customer(0, fn, ln, email, phone));
        System.out.println("✅ Customer added!");
    }

    private static void removeCustomer(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("Enter Customer ID: ");
        dao.removeCustomer(sc.nextInt());
        System.out.println("🗑 Customer removed!");
    }

    private static void listCustomers(ICarLeaseRepository dao) {
        var customers = dao.listCustomers();
        if (customers.isEmpty()) {
            System.out.println("⚠️ No customers found in the database.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void findCustomer(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("Enter Customer ID: ");
        try {
            System.out.println(dao.findCustomerById(sc.nextInt()));
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addCar(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("Make: ");
        String make = sc.nextLine();
        System.out.print("Model: ");
        String model = sc.nextLine();
        System.out.print("Year: ");
        int year = sc.nextInt();
        System.out.print("Daily Rate: ");
        double rate = sc.nextDouble();
        System.out.print("Passenger Capacity: ");
        int capacity = sc.nextInt();
        System.out.print("Engine Capacity: ");
        int engine = sc.nextInt();
        sc.nextLine();
        dao.addCar(new Car(0, make, model, year, rate, "available", capacity, engine));
        System.out.println("✅ Car added!");
    }

    private static void removeCar(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("Enter Car ID: ");
        dao.removeCar(sc.nextInt());
        System.out.println("🗑 Car removed!");
    }

    private static void listAvailableCars(ICarLeaseRepository dao) {
        var cars = dao.listAvailableCars();
        if (cars.isEmpty()) {
            System.out.println("⚠️ No available cars found in the database.");
        } else {
            cars.forEach(System.out::println);
        }
    }

    private static void listRentedCars(ICarLeaseRepository dao) {
        var cars = dao.listRentedCars();
        if (cars.isEmpty()) {
            System.out.println("⚠️ No rented cars found in the database.");
        } else {
            cars.forEach(System.out::println);
        }
    }

    private static void findCar(Scanner sc, ICarLeaseRepository dao) {
        System.out.print("Enter Car ID: ");
        try {
            System.out.println(dao.findCarById(sc.nextInt()));
        } catch (CarNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
