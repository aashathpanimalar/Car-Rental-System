package org.carrental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url="jdbc:mysql://localhost:3306/car_rental_db";
                String user="root";
                String password="Myrootv@123";
                // Connect to database
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ MySQL Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ Database connection failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
