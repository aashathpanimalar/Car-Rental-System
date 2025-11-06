package org.carrental.main;

import org.carrental.util.DBConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            // Use your DBConnUtil to get connection
            Connection conn = DBConnUtil.getConnection();
            if (conn != null) {
                System.out.println("Database connected successfully!");
                conn.close();
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
