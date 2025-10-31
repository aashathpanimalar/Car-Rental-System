package org.carrental.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertiesUtil {

    public static Properties loadProperties() {
        Properties props = new Properties();

        try (InputStream input = DBPropertiesUtil.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                System.out.println("❌ db.properties not found in resources folder!");
                return props;
            }

            props.load(input);


//            System.out.println(" Loaded DB Properties:");
//            System.out.println("URL: " + props.getProperty("db.url"));
//            System.out.println("User: " + props.getProperty("db.username"));

        } catch (IOException e) {
            System.out.println("❌ Error loading db.properties file!");
            e.printStackTrace();
        }

        return props;
    }
}
