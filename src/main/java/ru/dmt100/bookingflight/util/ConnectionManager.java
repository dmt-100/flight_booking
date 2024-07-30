package ru.dmt100.bookingflight.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "spring.datasource.url";
    private static final String USERNAME_KEY = "spring.datasource.username";
    private static final String PASSWORD_KEY = "spring.datasource.password";

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertyUtil.getKey(URL_KEY),
                    PropertyUtil.getKey(USERNAME_KEY),
                    PropertyUtil.getKey(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
