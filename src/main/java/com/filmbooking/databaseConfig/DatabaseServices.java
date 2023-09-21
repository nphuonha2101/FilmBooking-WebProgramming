package com.filmbooking.databaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServices {
    private Connection connection = null;

    public boolean connectDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            String DB_URL = "jdbc:postgresql://localhost:5432/FilmBooking";
            String USER_NAME = "postgres";
            String PASSWORD = "nphuonha";

            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

            if (connection != null) {
                System.out.println("Connect successfully!");
                return true;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void disconnectDatabase() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
