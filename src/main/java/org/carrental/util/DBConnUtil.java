package org.carrental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnUtil {

    private static Connection conn;

    public static Connection getConnection() {
        try {
            // Load properties using the helper class
            Properties props = DBPropertiesUtil.loadProperties();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String pass = props.getProperty("db.password");

            // Load MySQL JDBC Driver --optional after jdbc 8.0
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while connecting!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ General error while establishing connection!");
            e.printStackTrace();
        }

        return conn;
    }
}