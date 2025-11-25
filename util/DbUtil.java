package com.carrental.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try (InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            if (in == null) {
                throw new RuntimeException("db.properties not found in classpath");
            }
            props.load(in);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
