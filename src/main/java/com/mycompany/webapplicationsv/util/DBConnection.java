package com.mycompany.webapplicationsv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database connection parameters
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_NAME = "webappdb";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    // JDBC URL
    private static final String JDBC_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/"
            + DATABASE_NAME + "?useSSL=false&serverTimezone=UTC";

    // Singleton Instance
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException {
        try {
            // Load JDBC driver
            Class.forName(JDBC_DRIVER);
            // Establish connection
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found: " + e.getMessage());
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getInstance().getConnection()) {
            return conn != null;
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        if (testConnection()) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed!");
        }
    }
}
