package ru.dmt100.flight_booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    Connection connection;

    public ConnectionManager() {
    }

    public Connection open() {
        try {
            String URL_KEY = "spring.datasource.url";
            String USERNAME_KEY = "spring.datasource.username";
            String PASSWORD_KEY = "spring.datasource.password";

            connection = DriverManager.getConnection(
                    PropertyUtil.getKey(URL_KEY),
                    PropertyUtil.getKey(USERNAME_KEY),
                    PropertyUtil.getKey(PASSWORD_KEY)
            );
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConnectionClosed(){
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return open();
    }

}
