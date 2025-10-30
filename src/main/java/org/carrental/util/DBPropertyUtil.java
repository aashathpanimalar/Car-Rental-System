package org.carrental.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties getProperties(String filename) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static String getConnectionString(Properties props) {
        String host = props.getProperty("hostname");
        String port = props.getProperty("port");
        String dbname = props.getProperty("dbname");
        return "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false&serverTimezone=UTC";
    }
}
